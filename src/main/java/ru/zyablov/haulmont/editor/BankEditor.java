package ru.zyablov.haulmont.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zyablov.haulmont.dao.BankDAO;
import ru.zyablov.haulmont.dao.CreditDAO;
import ru.zyablov.haulmont.dao.CustomerDAO;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.CreditOffer;
import ru.zyablov.haulmont.entity.Customer;


@SpringComponent
@UIScope
public class BankEditor extends VerticalLayout implements KeyNotifier {

    private Bank bank;
    private BankDAO bankDAO = new BankDAO();
    private CreditDAO creditDAO = new CreditDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    private ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
    private ComboBox<Customer> customerComboBox = new ComboBox<>("Customer");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Bank> binder = new Binder<>(Bank.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public BankEditor() {

//        creditComboBox.setItems(creditDAO.findAll());
//        creditComboBox.setItemLabelGenerator(Credit::toString);
//        creditComboBox.setPageSize(100);
//        customerComboBox.setItems(customerDAO.findAll());
//        customerComboBox.setItemLabelGenerator(Customer::toString);
//        customerComboBox.setPageSize(100);

        add(actions);


//        binder.forField(creditComboBox)
//                .withValidator(new BeanValidator(CreditOffer.class, "credit"))
//                .bind(Bank::getCredits, Bank::setCredits);
//
//        binder.forField(customerComboBox)
////                .withValidator(new BeanValidator(CreditOffer.class, "credit"))
//                .bind(CreditOffer::getCustomer, CreditOffer::setCustomer);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editBank(bank));
        setVisible(false);
    }

    void delete() {
        bankDAO.delete(bank);

        changeHandler.onChange();
    }

    void save() {
        if (this.bank.getId()!=0){
            bankDAO.update(this.bank);
        }else {
        bankDAO.save(bank);}
        changeHandler.onChange();
    }


    public final void editBank(Bank bank) {
        if (bank == null) {
            setVisible(false);
            return;
        }

        if (bank.getId() != 0) {
            this.bank = bankDAO.getById(bank.getId());
        } else {
            this.bank = bank;
        }

        binder.setBean(this.bank);

        setVisible(true);

//        .focus();
    }

}