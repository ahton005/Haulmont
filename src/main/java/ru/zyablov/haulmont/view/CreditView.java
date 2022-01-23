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
import ru.zyablov.haulmont.dao.CreditDAO;
import ru.zyablov.haulmont.dao.DaoServices;
import ru.zyablov.haulmont.editor.CreditEditor;
import ru.zyablov.haulmont.entity.Credit;

@Route(value = "credit",layout = MainLayout.class)
@PageTitle("Credits")
public class CreditView extends VerticalLayout {

    private DaoServices<Credit> creditDaoServices = new CreditDAO();
    final Grid<Credit> creditGrid;
    private final CreditEditor creditEditor;
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("New credit", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    public CreditView(CreditEditor creditEditor) {
        this.creditEditor = creditEditor;
        this.creditGrid = new Grid<>(Credit.class);
        this.creditGrid.setColumns("id","percent","creditLimit");
        filter.setPlaceholder("Find by percent...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, creditGrid, creditEditor);


        creditGrid
                .asSingleSelect()
                .addValueChangeListener(e -> creditEditor.editCredit(e.getValue()));

        addNewButton.addClickListener(e -> creditEditor.editCredit(new Credit()));

        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList("");

    }


    private void fillList(String string) {

            creditGrid.setItems(creditDaoServices.findAll());

    }

}