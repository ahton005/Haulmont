package ru.zyablov.haulmont.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.zyablov.haulmont.dao.BankDAO;
import ru.zyablov.haulmont.dao.DaoServices;
import ru.zyablov.haulmont.editor.BankEditor;
import ru.zyablov.haulmont.editor.CreditEditor;
import ru.zyablov.haulmont.entity.Bank;

@Route(value = "banks",layout = MainLayout.class)
@PageTitle("Banks")
public class BankView extends VerticalLayout {

    private DaoServices<Bank> creditDaoServices = new BankDAO();
    final Grid<Bank> bankGrid;
    private final BankEditor bankEditor;
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("New bank", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    public BankView(BankEditor bankEditor) {
        this.bankEditor = bankEditor;
        this.bankGrid = new Grid<>(Bank.class);
        this.bankGrid.setColumns("id");
        filter.setPlaceholder("Find by percent...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, bankGrid, bankEditor);


        bankGrid
                .asSingleSelect()
                .addValueChangeListener(e -> bankEditor.editBank(e.getValue()));

        addNewButton.addClickListener(e -> bankEditor.editBank(new Bank()));

        bankEditor.setChangeHandler(() -> {
            bankEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList("");

    }


    private void fillList(String string) {

            bankGrid.setItems(creditDaoServices.findAll());

    }

}