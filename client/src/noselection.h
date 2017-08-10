#ifndef NOSELECTION_H
#define NOSELECTION_H

#include <QWidget>

namespace Ui {
class NoSelection;
}

class NoSelection : public QWidget
{
        Q_OBJECT

public:
        explicit NoSelection(QWidget *parent = 0);
        ~NoSelection();

private:
        Ui::NoSelection *ui;
};

#endif // NOSELECTION_H
