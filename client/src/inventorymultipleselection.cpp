#include "inventorymultipleselection.h"
#include "ui_inventorymultipleselection.h"

InventoryMultipleSelection::InventoryMultipleSelection(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::InventoryMultipleSelection)
{
        ui->setupUi(this);
}

InventoryMultipleSelection::~InventoryMultipleSelection()
{
        delete ui;
}
