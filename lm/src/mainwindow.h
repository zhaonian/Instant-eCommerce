#ifndef MAINWINDOW_H
#define MAINWINDOW_H


#include "auth.h"

#include <QMainWindow>
#include <QTreeWidget>
#include "localdb.h"
#include "noselection.h"
#include "inventorycreation.h"
#include "inventorysingleselection.h"
#include "inventorymultipleselection.h"
#include "inventorytracking.h"


namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
        Q_OBJECT

public:
        explicit MainWindow(QWidget *parent = 0);
        ~MainWindow();

        void    make_connection(core::identity const& identity);

private slots:
        void on_import_master_listings_triggered();

        void on_import_system_backup_triggered();

        void on_export_system_backup_triggered();

        void on_open_account_manager_triggered();

        void on_open_server_connection_triggered();

        void on_open_history_triggered();

        void on_publish_triggered();

        void on_help_about_triggered();

        void on_revert_to_last_commit_triggered();

        void on_commit_triggered();

private:
        Ui::MainWindow*                 m_ui;
        core::localdb*                  m_localdb = nullptr;
        NoSelection                     m_no_selection;
        InventoryCreation               m_creation;
        InventorySingleSelection        m_single_selection;
        InventoryMultipleSelection      m_multiple_selection;

        QTreeWidgetItem*                m_inventory_container = nullptr;
};

#endif // MAINWINDOW_H
