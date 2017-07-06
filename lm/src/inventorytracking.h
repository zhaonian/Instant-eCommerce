#ifndef INVENTORYTRACKING_H
#define INVENTORYTRACKING_H

#include <QWidget>

namespace Ui {
class InventoryTracking;
}

class InventoryTracking : public QWidget
{
        Q_OBJECT

public:
        explicit InventoryTracking(QWidget *parent = 0);
        ~InventoryTracking();

private:
        Ui::InventoryTracking *ui;
};

#endif // INVENTORYTRACKING_H
