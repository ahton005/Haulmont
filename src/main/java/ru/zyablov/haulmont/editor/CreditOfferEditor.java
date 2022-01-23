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
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zyablov.haulmont.dao.CreditDAO;
import ru.zyablov.haulmont.dao.CreditOfferDAO;
import ru.zyablov.haulmont.dao.CustomerDAO;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.CreditOffer;
import ru.zyablov.haulmont.entity.Customer;


@SpringComponent
@UIScope
public class CreditOfferEditor extends VerticalLayout implements KeyNotifier {

    private CreditOffer creditOffer;
    private CreditOfferDAO creditOfferDAO = new CreditOfferDAO();
    private CreditDAO creditDAO = new CreditDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    TextField loan_amount = new TextField("Loan amount", "Loan amount");
    private ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
    private ComboBox<Customer> customerComboBox = new ComboBox<>("Customer");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CreditOfferEditor() {
        creditComboBox.setItems(creditDAO.findAll());
        creditComboBox.setItemLabelGenerator(Credit::toString);
        creditComboBox.setPageSize(100);
        customerComboBox.setItems(customerDAO.findAll());
        customerComboBox.setItemLabelGenerator(Customer::toString);
        customerComboBox.setPageSize(100);

        add(creditComboBox,customerComboBox,loan_amount,actions);

        binder.forField(loan_amount)

                .withConverter(new StringToLongConverter("Not a number")).bind(x-> x.getSumOfCredit(),
                        CreditOffer::setSumOfCredit);
        binder.forField(creditComboBox)
//                .withValidator(new BeanValidator(CreditOffer.class, "credit"))
                .bind(CreditOffer::getCredit, CreditOffer::setCredit);

        binder.forField(customerComboBox)
//                .withValidator(new BeanValidator(CreditOffer.class, "credit"))
                .bind(CreditOffer::getCustomer, CreditOffer::setCustomer);



        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCreditOffer(creditOffer));
        setVisible(false);
    }

    void delete() {
        creditOfferDAO.delete(creditOffer);

        changeHandler.onChange();
    }

    void save() {
        if (this.creditOffer.getId()!=0){
            creditOfferDAO.update(this.creditOffer);
        }else {
        creditOfferDAO.save(creditOffer);}
        changeHandler.onChange();
    }


    public final void editCreditOffer(CreditOffer creditOffer) {
        if (creditOffer == null) {
            setVisible(false);
            return;
        }

        if (creditOffer.getId() != 0) {
            this.creditOffer = creditOfferDAO.getById(creditOffer.getId());
        } else {
            this.creditOffer = creditOffer;
        }

        binder.setBean(this.creditOffer);

        setVisible(true);

//        .focus();
    }

}