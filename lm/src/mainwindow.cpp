#include "server.h"
#include "mainwindow.h"
#include "ui_mainwindow.h"



static void
update_inventory_container(QTreeWidget* browser, QTreeWidgetItem** container, std::string const& db_name)
{
        delete *container;
        *container = new QTreeWidgetItem(browser);
        (*container)->setText(0, db_name.c_str());
        browser->insertTopLevelItem(0, *container);
}

MainWindow::MainWindow(QWidget *parent) :
        QMainWindow(parent),
        m_ui(new Ui::MainWindow),
        m_identity("Null")
{
        m_ui->setupUi(this);

        m_ui->main_window_layout->addWidget(&m_no_selection);
}

MainWindow::~MainWindow()
{
        delete m_ui;
}

void
MainWindow::make_connection(core::identity const& identity)
{
        m_identity = identity;

        core::central_server* server = core::get_central_server();

        std::string const& user_name = identity.user_name();
        std::string const& db_name = server->connection_name();

        update_inventory_container(m_ui->inventory_browser, &m_inventory_container, db_name);

        m_ui->statusBar->showMessage(("Welcome " + user_name + ". You have connected to the database " + db_name).c_str());
}


void MainWindow::on_import_master_listings_triggered()
{
}

void MainWindow::on_import_system_backup_triggered()
{
}

void MainWindow::on_export_system_backup_triggered()
{
}

void MainWindow::on_open_account_manager_triggered()
{
}

void MainWindow::on_open_server_connection_triggered()
{
}

void MainWindow::on_open_history_triggered()
{
}

void MainWindow::on_revert_to_last_commit_triggered()
{
}

void MainWindow::on_commit_triggered()
{
}

void MainWindow::on_publish_triggered()
{
}

void MainWindow::on_help_about_triggered()
{
}
