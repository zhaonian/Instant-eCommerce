#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
        QMainWindow(parent),
        ui(new Ui::MainWindow)
{
        ui->setupUi(this);

        ui->statusBar->showMessage("Welcome.");
}

MainWindow::~MainWindow()
{
        delete ui;
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
