#ifndef INVENTORYSINGLESELECTION_H
#define INVENTORYSINGLESELECTION_H

#include <QWidget>

namespace Ui {
class InventorySingleSelection;
}

class InventorySingleSelection : public QWidget
{
        Q_OBJECT

public:
        explicit InventorySingleSelection(QWidget *parent = 0);
        ~InventorySingleSelection();

private:
        Ui::InventorySingleSelection *ui;
};

#endif // INVENTORYSINGLESELECTION_H
