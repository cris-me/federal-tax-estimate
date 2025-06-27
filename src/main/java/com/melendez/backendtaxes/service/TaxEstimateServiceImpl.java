package com.melendez.backendtaxes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melendez.backendtaxes.customExceptions.TaxReturnWithEmailDoesNotExistException;
import com.melendez.backendtaxes.customExceptions.UserNotFoundException;
import com.melendez.backendtaxes.models.IncomeSource;
import com.melendez.backendtaxes.models.TaxReturn;
import com.melendez.backendtaxes.models.User;
import com.melendez.backendtaxes.repositories.TaxEstimateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxEstimateServiceImpl implements TaxEstimateService {

    private final UsersService usersService;
    private final TaxEstimateRepository repository;
    private final IncomeSourcesService incomeSourcesService;

    private User findUser(String email) {
        Optional<User> user = usersService.getOneUser(email);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Requested user not found");
        }
        return user.get();
    }

    // Show one return by email. If not present then one is generated
    public TaxReturn findReturnByEmail(String email, int year) {
        User user = findUser(email);
        Optional<TaxReturn> taxReturn = repository.findByUserIdAndYear(user.getId(), year);
        if (taxReturn.isPresent()) {
            log.info("Requested form found");
            return taxReturn.get();
        } else {
            log.info("No form found. Creating new form.");
            return saveTaxReturn(email, year);
        }
    }

    // Delete a tax return by passing in the email
    public void deleteReturnByEmail(String email, int year) {
        User user = findUser(email);
        Optional<TaxReturn> returnToDelete = repository.findByUserIdAndYear(user.getId(), year);
        if (returnToDelete.isPresent()) {
            repository.delete(returnToDelete.get());
        } else {
            throw new TaxReturnWithEmailDoesNotExistException(
                    "No tax return found for the provided email address and year");
        }
    }

    // Saves a new tax return to the database or updates an old one
    public TaxReturn saveTaxReturn(String email, int year) {
        log.info("Creating a new {} tax return for email {}", year, email);
        User user = findUser(email);
        String userId = user.getId();
        // find existing form to reuse id or create new one
        Optional<TaxReturn> existingReturn = repository.findByUserIdAndYear(userId, year);
        TaxReturn taxReturn = new TaxReturn();
        if (existingReturn.isPresent()) {
            taxReturn.setId(existingReturn.get().getId());
        }
        // connect form to user
        taxReturn.setUserId(userId);
        taxReturn.setYear(year);
        taxReturn.setFilingStatus(user.getFilingStatus());

        double totalIncome = 0;
        double totalTaxWithheld = 0;
        double taxableIncome = 0;
        double estimatedTaxOwed = 0;

        // gather income sources
        List<IncomeSource> incomeSources = incomeSourcesService.getIncomeSourcesByUserAndYear(userId, year);
        for (IncomeSource incomeSource : incomeSources) {
            totalIncome += incomeSource.getIncome();
            totalTaxWithheld += incomeSource.getTaxWithheld();
        }

        taxReturn.setTotalIncome(totalIncome);
        taxReturn.setTotalWithheld(totalTaxWithheld);
        taxableIncome = totalIncome - determineStandardDeduction(user.getFilingStatus());
        taxReturn.setTaxableIncome(taxableIncome);
        estimatedTaxOwed = determineIncomeTax(user.getFilingStatus(), taxableIncome);
        taxReturn.setEstimatedTaxOwed(estimatedTaxOwed);

        // FEDERAL TAX CALCULATIONS

        // TAX CREDIT CALCULATIONS
        // Determine credits for child
        double totalChildCredit = determineChildTaxCredit(taxableIncome, taxReturn.getFilingStatus(),
                user.getChildDependents());
        taxReturn.setTotalCredits(totalChildCredit);

        // Subtracting credits and amount withheld from how much is owed estimate
        taxReturn.setRefundOrOwed((int) (estimatedTaxOwed - totalChildCredit - totalTaxWithheld));
        log.info("Tax form created");
        return repository.save(taxReturn);
    }

    // Determining a child tax credit is dependent on the modified adjusted gross
    // income (MAGI) of the applicant and their filing status
    private double determineChildTaxCredit(double MAGI, String filingStatus, int claims) {
        if (filingStatus.equals("MJ")) {
            // If MJ and above threshold, no child tax credit
            if (MAGI > 440000) {
                return 0;
            }
            // If within 400,000 and 440,000 then use calculation formula
            else if (MAGI >= 400000) {
                return claims * (2000 - (MAGI - 400000) * .05);
            }
            // Below 400,000 no calculation needed, $2K per child
            else {
                return 2000 * claims;
            }
        } else {
            // If above threshold, no child tax credit
            if (MAGI > 240000) {
                return 0;
            }
            // If within 200,000 and 240,000 then use calculation formula
            else if (MAGI >= 200000) {
                return claims * (2000 - (MAGI - 200000) * .05);
            }
            // Below 200,000 no calculation needed, $2K per child
            else {
                return 2000 * claims;
            }
        }
    }

    private double determineStandardDeduction(String filingStatus) {
        switch (filingStatus) {
            case "S":
                return 14600;
            case "MS":
                return 14600;
            case "MJ":
                return 29200;
            case "H":
                return 21900;
            default:
                return 0;
        }
    }

    private double determineIncomeTax(String filingStatus, double taxableIncome) {
        // Tax Bracket calculations - calculates tax owed based on tax bracket and
        // filing status then subtracts federal tax already withheld
        if (taxableIncome <= 0) {
            return 0;
        } else {
            switch (filingStatus) {
                case "S":
                    if (taxableIncome <= 11600) {
                        return (taxableIncome * .1);
                    } else if (taxableIncome <= 47150) {
                        return (((taxableIncome - 11600) * .12) + 1160);
                    } else if (taxableIncome <= 100525) {
                        return (((taxableIncome - 47150) * .22) + 5426);
                    } else if (taxableIncome <= 191950) {
                        return (((taxableIncome - 100525) * .24) + 17169);
                    } else if (taxableIncome <= 243725) {
                        return (((taxableIncome - 191950) * .32) + 39111);
                    } else if (taxableIncome <= 609350) {
                        return (((taxableIncome - 243725) * .35) + 55679);
                    } else {
                        return (((taxableIncome - 609350) * .37) + 183647);
                    }
                case "MJ":
                    if (taxableIncome <= 23200) {
                        return ((taxableIncome * .1));
                    } else if (taxableIncome <= 94300) {
                        return (((taxableIncome - 23200) * .12) + 2320);
                    } else if (taxableIncome <= 201050) {
                        return (((taxableIncome - 94300) * .22) + 10852);
                    } else if (taxableIncome <= 383900) {
                        return (((taxableIncome - 201050) * .24) + 34337);
                    } else if (taxableIncome <= 487450) {
                        return (((taxableIncome - 383900) * .32) + 78221);
                    } else if (taxableIncome <= 731200) {
                        return (((taxableIncome - 487450) * .35) + 111357);
                    } else {
                        return (((taxableIncome - 731200) * .37) + 196670);
                    }
                case "MS":
                    if (taxableIncome <= 11925) {
                        return ((taxableIncome * .1));
                    } else if (taxableIncome <= 48475) {
                        return (((taxableIncome - 11925) * .12) + 1193);
                    } else if (taxableIncome <= 103350) {
                        return (((taxableIncome - 48475) * .22) + 5579);
                    } else if (taxableIncome <= 197300) {
                        return (((taxableIncome - 103350) * .24) + 17651);
                    } else if (taxableIncome <= 250525) {
                        return (((taxableIncome - 197300) * .32) + 40199);
                    } else if (taxableIncome <= 375800) {
                        return (((taxableIncome - 250525) * .35) + 57231);
                    } else {
                        return (((taxableIncome - 375800) * .37) + 101077);
                    }
                case "H":
                    if (taxableIncome <= 16550) {
                        return ((taxableIncome * .1));
                    } else if (taxableIncome <= 63100) {
                        return (((taxableIncome - 16550) * .12) + 1655);
                    } else if (taxableIncome <= 100500) {
                        return (((taxableIncome - 63100) * .22) + 7241);
                    } else if (taxableIncome <= 191950) {
                        return (((taxableIncome - 100500) * .24) + 15469);
                    } else if (taxableIncome <= 243700) {
                        return (((taxableIncome - 191950) * .32) + 37417);
                    } else if (taxableIncome <= 609350) {
                        return (((taxableIncome - 243700) * .35) + 53977);
                    } else {
                        return (((taxableIncome - 609350) * .37) + 181955);
                    }
                default:
                    return 0;
            }
        }
    }

}
