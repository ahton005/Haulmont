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
import ru.zyablov.haulmont.dao.CreditOfferDAO;
import ru.zyablov.haulmont.dao.DaoServices;
import ru.zyablov.haulmont.editor.BankEditor;
import ru.zyablov.haulmont.editor.CreditOfferEditor;
import ru.zyablov.haulmont.entity.Bank;
import ru.zyablov.haulmont.entity.CreditOffer;

@Route(value = "credit_offers",layout = MainLayout.class)
@PageTitle("Credit offers")
public class CreditOfferView extends VerticalLayout {

    private DaoServices<CreditOffer> creditOfferDAO = new CreditOfferDAO();
    final Grid<CreditOffer> creditOfferGrid;
    private final CreditOfferEditor creditOfferEditor;
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("New credit offer", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    public CreditOfferView(CreditOfferEditor creditOfferEditor) {
        this.creditOfferEditor = creditOfferEditor;
        this.creditOfferGrid = new Grid<>(CreditOffer.class);
        this.creditOfferGrid.setColumns("id","customer","sumOfCredit","credit");
        filter.setPlaceholder("Find by percent...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, creditOfferGrid, creditOfferEditor);


        creditOfferGrid
                .asSingleSelect()
                .addValueChangeListener(e -> creditOfferEditor.editCreditOffer(e.getValue()));

        addNewButton.addClickListener(e -> creditOfferEditor.editCreditOffer(new CreditOffer()));

        creditOfferEditor.setChangeHandler(() -> {
            creditOfferEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList("");

    }


    private void fillList(String string) {

            creditOfferGrid.setItems(creditOfferDAO.findAll());

    }

}