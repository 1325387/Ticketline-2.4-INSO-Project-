package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The bank account entity.
 */
@Entity
public class BankAccount extends MethodOfPayment {

    private static final long serialVersionUID = -5767470592696077401L;

    private String owner;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String bankCode;

    private String IBAN;

    private String BIC;

    /**
     * Instantiates a new bank account.
     */
    public BankAccount() {
    }

    /**
     * Instantiates a new bank account.
     *
     * @param id the id
     * @param accountNumber the account number
     * @param bankCode the bank code
     */
    public BankAccount(Integer id, String accountNumber, String bankCode) {
        super(id, null);
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
    }

    /**
     * Instantiates a new bank account.
     *
     * @param id the id
     * @param deleted the deleted
     * @param bank the bank
     * @param accountNumber the account number
     * @param bankCode the bank code
     * @param iban the iban
     * @param bic the bic
     */
    public BankAccount(Integer id, Boolean deleted, String bank, String accountNumber, String bankCode,
                       String iban, String bic) {
        super(id, deleted);
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.IBAN = iban;
        this.BIC = bic;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the bank.
     *
     * @return the bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the bank.
     *
     * @param bank the new bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Gets the account number.
     *
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param accountNumber the new account number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the bank code.
     *
     * @return the bank code
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the bank code.
     *
     * @param bankCode the new bank code
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * Gets the iban.
     *
     * @return the iban
     */
    public String getIBAN() {
        return IBAN;
    }

    /**
     * Sets the iban.
     *
     * @param iBAN the new iban
     */
    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }

    /**
     * Gets the bic.
     *
     * @return the bic
     */
    public String getBIC() {
        return BIC;
    }

    /**
     * Sets the bic.
     *
     * @param bIC the new bic
     */
    public void setBIC(String bIC) {
        BIC = bIC;
    }

}
