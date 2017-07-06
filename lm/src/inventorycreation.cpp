#include "inventorycreation.h"
#include "ui_inventorycreation.h"

InventoryCreation::InventoryCreation(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::InventoryCreation)
{
        ui->setupUi(this);
}

InventoryCreation::~InventoryCreation()
{
        delete ui;
}
