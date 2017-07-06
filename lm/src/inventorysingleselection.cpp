#include "inventorysingleselection.h"
#include "ui_inventorysingleselection.h"

InventorySingleSelection::InventorySingleSelection(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::InventorySingleSelection)
{
        ui->setupUi(this);
}

InventorySingleSelection::~InventorySingleSelection()
{
        delete ui;
}
