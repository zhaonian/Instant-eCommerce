#include "noselection.h"
#include "ui_noselection.h"


NoSelection::NoSelection(QWidget *parent) :
        QWidget(parent),
        ui(new Ui::NoSelection)
{
        ui->setupUi(this);
}

NoSelection::~NoSelection()
{
        delete ui;
}
