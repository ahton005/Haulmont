package ru.zyablov.haulmont.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zyablov.haulmont.dao.CustomerDAO;
import ru.zyablov.haulmont.entity.Customer;


@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {

    private Customer customer;
    private CustomerDAO customerDAO = new CustomerDAO();

    TextField fio = new TextField("", "fio");
    TextField phone = new TextField("", "Phone number");
    TextField email = new TextField("", "E-mail");
    TextField passNum = new TextField("", "Passport number");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Customer> binder = new Binder<>(Customer.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CustomerEditor() {

        add(fio, phone, email, passNum, actions);

        // bind using naming convention
        binder.forField(fio)

                .bind(Customer::getFio,
                        Customer::setFio);
        binder.forField(phone)

                .bind(Customer::getPhoneNumber,
                        Customer::setPhoneNumber);
        binder.forField(email)

                .bind(Customer::getEmail,
                        Customer::setEmail);
        binder.forField(passNum)

                .bind(Customer::getNumberPassport,
                        Customer::setNumberPassport);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(customer));
        setVisible(false);
    }

    void delete() {
        customerDAO.delete(customer);

        changeHandler.onChange();
    }

    void save() {
        if (this.customer.getId()!=null){
            customerDAO.update(this.customer);
        }else {
        customerDAO.save(customer);}
        changeHandler.onChange();
    }


    public final void editCustomer(Customer c) {
        if (c == null) {
            setVisible(false);
            return;
        }

        if (c.getId() != null) {
            this.customer = customerDAO.getById(c.getId());
        } else {
            this.customer = c;
        }

        binder.setBean(customer);

        setVisible(true);

        fio.focus();
    }

}