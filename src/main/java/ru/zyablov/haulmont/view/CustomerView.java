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
import ru.zyablov.haulmont.dao.CustomerDAO;
import ru.zyablov.haulmont.dao.DaoServices;
import ru.zyablov.haulmont.editor.CustomerEditor;
import ru.zyablov.haulmont.entity.Customer;

@Route(value="",layout = MainLayout.class)

@PageTitle("Customers")
public class CustomerView extends VerticalLayout {

    private DaoServices<Customer> customerDaoServices = new CustomerDAO();
    final Grid<Customer> customerGrid;
    private final CustomerEditor customerEditor;
    private final TextField filter = new TextField();
    private final Button addNewButton = new Button("New customer", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    public CustomerView(CustomerEditor customerEditor) {
        this.customerEditor = customerEditor;
        this.customerGrid = new Grid<>(Customer.class);
        this.customerGrid.setColumns("id","fio","email","phoneNumber","numberPassport");
        filter.setPlaceholder("Find by fio...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, customerGrid, customerEditor);


        customerGrid
                .asSingleSelect()
                .addValueChangeListener(e -> customerEditor.editCustomer(e.getValue()));

        addNewButton.addClickListener(e -> customerEditor.editCustomer(new Customer()));

        customerEditor.setChangeHandler(() -> {
            customerEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList("");

    }


    private void fillList(String name) {
        if (name.isEmpty()) {
            customerGrid.setItems(customerDaoServices.findAll());
        } else {
            customerGrid.setItems(customerDaoServices.getByFIO(name));
        }
    }

}
