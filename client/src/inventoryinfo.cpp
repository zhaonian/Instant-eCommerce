#include "inventoryinfo.h"
#include "ui_inventoryinfo.h"

InventoryInfo::InventoryInfo(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::InventoryInfo)
{
        ui->setupUi(this);
}

InventoryInfo::~InventoryInfo()
{
        delete ui;
}
