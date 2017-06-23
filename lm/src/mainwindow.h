#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
        Q_OBJECT

public:
        explicit MainWindow(QWidget *parent = 0);
        ~MainWindow();

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
        Ui::MainWindow *ui;
};

#endif // MAINWINDOW_H
