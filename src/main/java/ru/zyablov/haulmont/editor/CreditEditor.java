package ru.zyablov.haulmont.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
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
import ru.zyablov.haulmont.dao.CreditDAO;
import ru.zyablov.haulmont.entity.Credit;
import ru.zyablov.haulmont.entity.Customer;


@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {

    private Credit credit;
    private CreditDAO creditDAO = new CreditDAO();

    TextField percent = new TextField("", "Percent");
    TextField limit = new TextField("", "Credit limit");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Credit> binder = new Binder<>(Credit.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CreditEditor() {

        add(percent, limit, actions);

        // bind using naming convention
        binder.forField(percent)

                .withConverter(new StringToLongConverter("Not a number")).bind(x-> x.getPercent(),
                        Credit::setPercent);
        binder.forField(limit)

                .withConverter(new StringToLongConverter("Not a number")).bind(Credit::getCreditLimit,
                        Credit::setCreditLimit);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCredit(credit));
        setVisible(false);
    }

    void delete() {
        creditDAO.delete(credit);

        changeHandler.onChange();
    }

    void save() {
        if (this.credit.getId()!=0){
            creditDAO.update(this.credit);
        }else {
        creditDAO.save(credit);}
        changeHandler.onChange();
    }


    public final void editCredit(Credit c) {
        if (c == null) {
            setVisible(false);
            return;
        }

        if (c.getId() != 0) {
            this.credit = creditDAO.getById(c.getId());
        } else {
            this.credit = c;
        }

        binder.setBean(credit);

        setVisible(true);

        percent.focus();
    }

}