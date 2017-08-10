#ifndef INVENTORYINFO_H
#define INVENTORYINFO_H

#include <QWidget>

namespace Ui {
class InventoryInfo;
}

class InventoryInfo : public QWidget
{
        Q_OBJECT

public:
        explicit InventoryInfo(QWidget *parent = 0);
        ~InventoryInfo();

private:
        Ui::InventoryInfo *ui;
};

#endif // INVENTORYINFO_H
