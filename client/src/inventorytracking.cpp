#include "inventorytracking.h"
#include "ui_inventorytracking.h"


InventoryTracking::InventoryTracking(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::InventoryTracking)
{
        ui->setupUi(this);
}

InventoryTracking::~InventoryTracking()
{
        delete ui;
}
