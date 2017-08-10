#ifndef INVENTORYMULTIPLESELECTION_H
#define INVENTORYMULTIPLESELECTION_H

#include <QWidget>

namespace Ui {
class InventoryMultipleSelection;
}

class InventoryMultipleSelection : public QWidget
{
        Q_OBJECT

public:
        explicit InventoryMultipleSelection(QWidget *parent = 0);
        ~InventoryMultipleSelection();

private:
        Ui::InventoryMultipleSelection *ui;
};

#endif // INVENTORYMULTIPLESELECTION_H
