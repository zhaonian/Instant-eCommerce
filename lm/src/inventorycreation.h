#ifndef INVENTORYCREATION_H
#define INVENTORYCREATION_H

#include <QWidget>

namespace Ui {
class InventoryCreation;
}

class InventoryCreation : public QWidget
{
        Q_OBJECT

public:
        explicit InventoryCreation(QWidget *parent = 0);
        ~InventoryCreation();

private:
        Ui::InventoryCreation *ui;
};

#endif // INVENTORYCREATION_H
