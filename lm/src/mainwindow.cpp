#include "server.h"
#include "mainwindow.h"
#include "ui_mainwindow.h"



static void
update_inventory_container(QTreeWidget* browser, QTreeWidgetItem** container, core::localdb& localdb)
{
        delete *container;
        *container = new QTreeWidgetItem(browser);
        (*container)->setText(0, localdb.connection_name().c_str());
        browser->insertTopLevelItem(0, *container);
}

MainWindow::MainWindow(QWidget *parent) :
        QMainWindow(parent),
        m_ui(new Ui::MainWindow)
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
        delete m_localdb;
        m_localdb = new core::localdb(*core::get_central_server(), identity);

        update_inventory_container(m_ui->inventory_browser, &m_inventory_container, *m_localdb);

        m_ui->statusBar->showMessage(("Welcome " + m_localdb->user_name() + ". You have connected to the database " + m_localdb->connection_name()).c_str());
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
