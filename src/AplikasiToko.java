import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AplikasiToko extends JFrame {

    final static String StringDriver = "com.mysql.cj.jdbc.Driver";
    final static String StringConnection = "jdbc:mysql://localhost:3306/javatgs?user=root&password=";

    private JDesktopPane frmMDI;
    private JInternalFrame frmBarang;
    private JInternalFrame frmCustomer;
    private JPanel pnlBarang;
    private JPanel pnlCustomer;
    private JMenuBar MenuBar = new JMenuBar();
    private JMenu MenuMaster = new JMenu("Master Data");
    private JMenuItem MenuBarang = new JMenuItem("Barang"),
            MenuCustomer = new JMenuItem("Customer");
    private JMenu MenuTransaksi = new JMenu("Transaksi");
    private JMenuItem MenuPenjualan = new JMenuItem("Penjualan"),
            MenuPembelian = new JMenuItem("Pembelian");
    private JMenuItem MenuExit = new JMenuItem("Exit");

    private static JLabel LblKodeBarang = new JLabel("Kode Barang");
    private static JTextField TxtKodeBarang = new JTextField();
    private static JLabel LblNamaBarang = new JLabel("Nama Barang");
    private static JTextField TxtNamaBarang = new JTextField();
    private static JLabel LblSatuanBarang = new JLabel("Satuan");
    private static JTextField TxtSatuanBarang = new JTextField();
    private static JLabel LblHargaBarang = new JLabel("Harga Barang");
    private static JTextField TxtHargaBarang = new JTextField();
    private static JLabel LblStockBarang = new JLabel("Stock Barang");
    private static JTextField TxtStockBarang = new JTextField();
    private static JButton TblBarangDelete = new JButton("Delete");
    private static JButton TblBarangSave = new JButton("Save");
    private static JButton TblBarangCancel = new JButton("Cancel");
    private static JLabel LblKodeCustomer = new JLabel("Kode Customer");
    private static JTextField TxtKodeCustomer = new JTextField();
    private static JLabel LblNamaCustomer = new JLabel("Nama Customer");
    private static JTextField TxtNamaCustomer = new JTextField();
    private static JLabel LblAlamatCustomer = new JLabel("Alamat Customer");
    private static JTextField TxtAlamatCustomer = new JTextField();
    private static JLabel LblNoTeleponCustomer = new JLabel("No. Telepon");

    private static JTextField TxtNoTeleponCustomer = new JTextField();
    private static JLabel LblEmailCustomer = new JLabel("Email");
    private static JTextField TxtEmailCustomer = new JTextField();
    private static JButton TblCustomerDelete = new JButton("Delete");
    private static JButton TblCustomerSave = new JButton("Save");
    private static JButton TblCustomerCancel = new JButton("Cancel");
    Dimension dimensi = Toolkit.getDefaultToolkit().getScreenSize();

    AplikasiToko() {
        super("Aplikasi Toko");
        setSize((int) (0.7 * dimensi.width), (int) (0.7 * dimensi.height));
        setLocation(dimensi.width / 2 - getWidth() / 2,
                dimensi.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frmMDI = new JDesktopPane();
        frmMDI.setLayout(null);
        // insets = frmMDI.getInsets();
        this.add(frmMDI);
        /* Menambahkan menu MDI */
        MenuMaster.add(MenuBarang);
        MenuMaster.add(MenuCustomer);
        MenuBar.add(MenuMaster);
        MenuTransaksi.add(MenuPenjualan);
        MenuTransaksi.add(MenuPembelian);
        MenuBar.add(MenuTransaksi);
        MenuBar.add(MenuExit);
        /* Mendeteksi event pada menu */
        MenuBarang.addActionListener(new MenuHandler());
        MenuCustomer.addActionListener(new MenuHandler());
        MenuExit.addActionListener(new MenuHandler());
        /* Mendeteksi event pada Button di Form Barang */
        TblBarangDelete.addActionListener(new TombolBarangHandler());
        TblBarangSave.addActionListener(new TombolBarangHandler());
        TblBarangCancel.addActionListener(new TombolBarangHandler());
        /* Mendeteksi event pada Button di Form Customer */
        TblCustomerDelete.addActionListener(new TombolCustomerHandler());
        TblCustomerSave.addActionListener(new TombolCustomerHandler());
        TblCustomerCancel.addActionListener(new TombolCustomerHandler());
        /* Mendeteksi event pada TxtKodeBarang di Form Barang */
        TxtKodeBarang.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    /* Mulai mencari data barang */
                    Boolean JDBC_Err = false;
                    Connection cn = null;
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                    } catch (Exception ex) {
                        JDBC_Err = true;
                        JOptionPane.showMessageDialog(null,
                                "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (!JDBC_Err) {
                        try {
                            cn = DriverManager.getConnection(StringConnection);
                            String SQLStatemen = "Select * from tbbarang where KodeBarang='"
                                    + TxtKodeBarang.getText() + "'";
                            Statement sta = cn.createStatement();
                            ResultSet rset = sta.executeQuery(SQLStatemen);
                            rset.first();
                            if (rset.getRow() > 0) {
                                TxtNamaBarang.setText(rset.getString("NamaBarang"));
                                TxtSatuanBarang.setText(rset.getString("SatuanBarang"));
                                TxtHargaBarang.setText(rset.getString("HargaBarang"));
                                TxtStockBarang.setText(rset.getString("StockBarang"));
                                sta.close();
                                rset.close();
                            } else {
                                sta.close();
                                rset.close();
                                ClearFormBarang();
                                JOptionPane.showMessageDialog(null, "Kode barang tidak ada");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Tidak dapat membuka tabel tbbarang\n" + ex, "Kesalahan",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    /* selesai mencari data barang */
                }
            }
        });
        /* Mendeteksi event pada TxtKodeCustomer di Form Customer */
        TxtKodeCustomer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    /* Mulai mencari data Customer */
                    Boolean JDBC_Err = false;
                    Connection cn = null;
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                    } catch (Exception ex) {
                        JDBC_Err = true;
                        JOptionPane.showMessageDialog(null,
                                "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (!JDBC_Err) {
                        try {
                            cn = DriverManager.getConnection(StringConnection);
                            String SQLStatemen = "Select * from tbcustomer where KodeCustomer='"
                                    + TxtKodeCustomer.getText() + "'";
                            Statement sta = cn.createStatement();
                            ResultSet rset = sta.executeQuery(SQLStatemen);
                            rset.first();

                            if (rset.getRow() > 0) {
                                TxtNamaCustomer.setText(rset.getString("NamaCustomer"));
                                TxtAlamatCustomer.setText(rset.getString("AlamatCustomer"));
                                TxtNoTeleponCustomer.setText(rset.getString("NoTelepon"));
                                TxtEmailCustomer.setText(rset.getString("Email"));
                                sta.close();
                                rset.close();
                            } else {
                                sta.close();
                                rset.close();
                                ClearFormCustomer();
                                JOptionPane.showMessageDialog(null, "Kode customer tidak ada");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Tidak dapat membuka tabel tbcustomer\n" + ex, "Kesalahan",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    /* selesai mencari data customer */
                }
            }
        });
        setContentPane(frmMDI);
        frmBarang = new JInternalFrame();
        frmBarang.setTitle("Master Data Barang");
        frmCustomer = new JInternalFrame("Master Data Customer");
        /* Pengaturan tampilan Form Barang */
        pnlBarang = new JPanel();
        LblKodeBarang.setBounds(30, 20, 160, 20);
        pnlBarang.add(LblKodeBarang);
        TxtKodeBarang.setBounds(120, 20, 100, 20);
        pnlBarang.add(TxtKodeBarang);
        LblNamaBarang.setBounds(30, 45, 160, 20);
        pnlBarang.add(LblNamaBarang);
        TxtNamaBarang.setBounds(120, 45, 200, 20);
        pnlBarang.add(TxtNamaBarang);
        LblSatuanBarang.setBounds(30, 70, 160, 20);
        pnlBarang.add(LblSatuanBarang);
        TxtSatuanBarang.setBounds(120, 70, 100, 20);
        pnlBarang.add(TxtSatuanBarang);
        LblHargaBarang.setBounds(30, 95, 160, 20);
        pnlBarang.add(LblHargaBarang);
        TxtHargaBarang.setBounds(120, 95, 100, 20);
        pnlBarang.add(TxtHargaBarang);
        LblStockBarang.setBounds(30, 120, 160, 20);
        pnlBarang.add(LblStockBarang);
        TxtStockBarang.setBounds(120, 120, 100, 20);
        pnlBarang.add(TxtStockBarang);
        /* Menampilkan tombol di form Barang */
        TblBarangDelete.setBounds(50, 180, 80, 30);
        pnlBarang.add(TblBarangDelete);
        TblBarangSave.setBounds(140, 180, 80, 30);
        pnlBarang.add(TblBarangSave);
        TblBarangCancel.setBounds(230, 180, 80, 30);

        pnlBarang.add(TblBarangCancel);
        pnlBarang.setLayout(null);
        frmBarang.add(pnlBarang);
        /* Pengaturan tampilan Form Customer */
        pnlCustomer = new JPanel();
        LblKodeCustomer.setBounds(30, 20, 160, 20);
        pnlCustomer.add(LblKodeCustomer);
        TxtKodeCustomer.setBounds(135, 20, 100, 20);
        pnlCustomer.add(TxtKodeCustomer);
        LblNamaCustomer.setBounds(30, 45, 160, 20);
        pnlCustomer.add(LblNamaCustomer);
        TxtNamaCustomer.setBounds(135, 45, 200, 20);
        pnlCustomer.add(TxtNamaCustomer);
        LblAlamatCustomer.setBounds(30, 70, 160, 20);
        pnlCustomer.add(LblAlamatCustomer);
        TxtAlamatCustomer.setBounds(135, 70, 320, 20);
        pnlCustomer.add(TxtAlamatCustomer);
        LblNoTeleponCustomer.setBounds(30, 95, 160, 20);
        pnlCustomer.add(LblNoTeleponCustomer);
        TxtNoTeleponCustomer.setBounds(135, 95, 200, 20);
        pnlCustomer.add(TxtNoTeleponCustomer);
        LblEmailCustomer.setBounds(30, 120, 160, 20);
        pnlCustomer.add(LblEmailCustomer);
        TxtEmailCustomer.setBounds(135, 120, 160, 20);
        pnlCustomer.add(TxtEmailCustomer);
        /* Menampilkan tombol di form Customer */
        TblCustomerDelete.setBounds(80, 180, 80, 30);
        pnlCustomer.add(TblCustomerDelete);
        TblCustomerSave.setBounds(170, 180, 80, 30);
        pnlCustomer.add(TblCustomerSave);
        TblCustomerCancel.setBounds(260, 180, 80, 30);
        pnlCustomer.add(TblCustomerCancel);
        pnlCustomer.setLayout(null);
        frmCustomer.add(pnlCustomer);
        frmMDI.add(frmBarang);
        frmMDI.add(frmCustomer);
        frmBarang.setBounds(10, 10, 367, 270);
        frmCustomer.setBounds(30, 30, 500, 270);
        setJMenuBar(MenuBar);
        setVisible(true);
    }

    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem M = (JMenuItem) e.getSource();
            if (M.getText().equals("Barang")) {
                frmBarang.setVisible(true);
            } else if (M.getText().equals("Customer")) {
                frmCustomer.setVisible(true);
            } else if (M.getText().equals("Exit")) {
                dispose();
            }
        }
    }

    private void ClearFormBarang() {
        TxtNamaBarang.setText("");
        TxtSatuanBarang.setText("");
        TxtHargaBarang.setText("");
        TxtStockBarang.setText("");
    }

    private void ClearFormCustomer() {
        TxtNamaCustomer.setText("");
        TxtAlamatCustomer.setText("");
        TxtNoTeleponCustomer.setText("");
        TxtEmailCustomer.setText("");
    }

    private class TombolBarangHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton TblPilih = (JButton) e.getSource();
            Boolean JDBC_Err = false;
            if (TblPilih.getText().equals("Delete")) {
                /* Mulai menghapus data barang */
                Connection cn = null;
                try {
                    cn = DriverManager.getConnection(StringConnection);
                } catch (Exception ex) {
                    JDBC_Err = true;
                    JOptionPane.showMessageDialog(null,
                            "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (!JDBC_Err) {
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                        String SQLStatemen = "SELECT * FROM tbbarang WHERE KodeBarang='" + TxtKodeBarang.getText()
                                + "'";
                        Statement sta = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rset = sta.executeQuery(SQLStatemen);

                        if (rset.next()) {
                            SQLStatemen = "DELETE FROM tbbarang WHERE KodeBarang='" + TxtKodeBarang.getText() + "'";
                            sta = cn.createStatement();
                            int simpan = sta.executeUpdate(SQLStatemen);
                            if (simpan == 1) {
                                TxtKodeBarang.setText("");
                                ClearFormBarang();
                                JOptionPane.showMessageDialog(null, "Sudah dihapus");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal menghapus data barang", "Kesalahan",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Kode barang tidak ada");
                        }

                        // Pastikan untuk menutup Statement dan ResultSet
                        sta.close();
                        rset.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Tidak dapat membuka tabel tbbarang\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                /* Selesai menghapus data barang */
            } else if (TblPilih.getText().equals("Save")) {
                /* Mulai menyimpan data barang */
                Connection cn = null;
                try {
                    cn = DriverManager.getConnection(StringConnection);
                } catch (Exception ex) {
                    JDBC_Err = true;
                    JOptionPane.showMessageDialog(null,
                            "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (!JDBC_Err) {
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                        String SQLStatemen = "SELECT * FROM tbbarang WHERE KodeBarang='" + TxtKodeBarang.getText()
                                + "'";
                        Statement sta = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rset = sta.executeQuery(SQLStatemen);

                        if (rset.next()) {
                            sta.close();
                            rset.close();
                            Object[] arrOpsi = { "Ya", "Tidak" };
                            int pilih = JOptionPane.showOptionDialog(null,
                                    "Kode Barang sudah ada\nApakah data diupdate?", "Konfirmasi",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                    arrOpsi, arrOpsi[0]);
                            if (pilih == 0) {
                                SQLStatemen = "UPDATE tbbarang SET NamaBarang='" + TxtNamaBarang.getText() +
                                        "', SatuanBarang='" + TxtSatuanBarang.getText() +
                                        "', HargaBarang='" + TxtHargaBarang.getText() +
                                        "', StockBarang='" + TxtStockBarang.getText() +
                                        "' WHERE KodeBarang='" + TxtKodeBarang.getText() + "'";
                                sta = cn.createStatement();
                                int simpan = sta.executeUpdate(SQLStatemen);
                                if (simpan == 1) {
                                    TxtKodeBarang.setText("");
                                    ClearFormBarang();
                                    JOptionPane.showMessageDialog(null, "Sudah tersimpan");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Gagal menyimpan data barang", "Kesalahan",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            sta.close();
                            rset.close();
                            SQLStatemen = "INSERT INTO tbbarang (KodeBarang, NamaBarang, SatuanBarang, HargaBarang, StockBarang) VALUES ('"
                                    +
                                    TxtKodeBarang.getText() + "','" + TxtNamaBarang.getText() +
                                    "','" + TxtSatuanBarang.getText() + "','" + TxtHargaBarang.getText() +
                                    "','" + TxtStockBarang.getText() + "')";
                            sta = cn.createStatement();
                            int simpan = sta.executeUpdate(SQLStatemen);
                            if (simpan == 1) {
                                TxtKodeBarang.setText("");
                                ClearFormBarang();
                                JOptionPane.showMessageDialog(null, "Sudah tersimpan");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal menyimpan data barang", "Kesalahan",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Tidak dapat membuka tabel tbbarang\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else if (TblPilih.getText().equals("Cancel")) {
                frmBarang.setVisible(false);
            }
        }
    }

    private class TombolCustomerHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton TblPilih = (JButton) e.getSource();
            Boolean JDBC_Err = false;
            if (TblPilih.getText().equals("Delete")) {
                /* Mulai menghapus data Customer */
                Connection cn = null;
                try {
                    cn = DriverManager.getConnection(StringConnection);
                } catch (Exception ex) {
                    JDBC_Err = true;
                    JOptionPane.showMessageDialog(null,
                            "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (!JDBC_Err) {
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                        String SQLStatemen = "SELECT * FROM tbcustomer WHERE KodeCustomer='" + TxtKodeCustomer.getText()
                                + "'";
                        Statement sta = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rset = sta.executeQuery(SQLStatemen);

                        if (rset.next()) {
                            // Tutup statement dan result set sebelum membuat statement baru
                            rset.close();
                            sta.close();

                            SQLStatemen = "DELETE FROM tbcustomer WHERE KodeCustomer='" + TxtKodeCustomer.getText()
                                    + "'";
                            sta = cn.createStatement();
                            int simpan = sta.executeUpdate(SQLStatemen);
                            if (simpan == 1) {
                                TxtKodeCustomer.setText("");
                                ClearFormCustomer();
                                JOptionPane.showMessageDialog(null, "Sudah dihapus");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal menghapus data customer", "Kesalahan",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Kode customer tidak ada");
                        }

                        // Pastikan untuk menutup Statement dan ResultSet setelah selesai digunakan
                        sta.close();
                        rset.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Tidak dapat membuka tabel tbcustomer\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                /* Selesai menghapus data Customer */
            } else if (TblPilih.getText().equals("Save")) {
                /* Mulai menyimpan data Customer */
                Connection cn = null;
                try {
                    cn = DriverManager.getConnection(StringConnection);
                } catch (Exception ex) {
                    JDBC_Err = true;
                    JOptionPane.showMessageDialog(null,
                            "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (!JDBC_Err) {
                    try {
                        cn = DriverManager.getConnection(StringConnection);
                        String SQLStatemen = "SELECT * FROM tbcustomer WHERE KodeCustomer='" + TxtKodeCustomer.getText()
                                + "'";
                        Statement sta = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        ResultSet rset = sta.executeQuery(SQLStatemen);

                        if (rset.next()) {
                            // Tutup statement dan result set sebelum membuat statement baru
                            rset.close();
                            sta.close();

                            Object[] arrOpsi = { "Ya", "Tidak" };
                            int pilih = JOptionPane.showOptionDialog(null,
                                    "Kode customer sudah ada\nApakah data diupdate?",
                                    "Konfirmasi", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, arrOpsi, arrOpsi[0]);
                            if (pilih == 0) {
                                SQLStatemen = "UPDATE tbcustomer SET NamaCustomer='"
                                        + TxtNamaCustomer.getText() + "', AlamatCustomer='"
                                        + TxtAlamatCustomer.getText() + "', NoTelepon='"
                                        + TxtNoTeleponCustomer.getText() + "', Email='"
                                        + TxtEmailCustomer.getText() + "' WHERE KodeCustomer='"
                                        + TxtKodeCustomer.getText() + "'";
                                sta = cn.createStatement();
                                int simpan = sta.executeUpdate(SQLStatemen);
                                if (simpan == 1) {
                                    TxtKodeCustomer.setText("");
                                    ClearFormCustomer();
                                    JOptionPane.showMessageDialog(null, "Sudah tersimpan");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Gagal menyimpan data customer", "Kesalahan",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            // Tidak ada data dengan KodeCustomer tersebut
                            rset.close();
                            sta.close();

                            SQLStatemen = "INSERT INTO tbcustomer (KodeCustomer, NamaCustomer, AlamatCustomer, NoTelepon, Email) VALUES ('"
                                    + TxtKodeCustomer.getText() + "','"
                                    + TxtNamaCustomer.getText() + "','"
                                    + TxtAlamatCustomer.getText() + "','"
                                    + TxtNoTeleponCustomer.getText() + "','"
                                    + TxtEmailCustomer.getText() + "')";
                            sta = cn.createStatement();
                            int simpan = sta.executeUpdate(SQLStatemen);
                            if (simpan == 1) {
                                TxtKodeCustomer.setText("");
                                ClearFormCustomer();
                                JOptionPane.showMessageDialog(null, "Sudah tersimpan");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal menyimpan data customer", "Kesalahan",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Tidak dapat membuka tabel tbcustomer\n" + ex, "Kesalahan",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                /* selesai menyimpan data Customer */
            } else if (TblPilih.getText().equals("Cancel")) {
                frmCustomer.setVisible(false);
            }
        }
    }

    public static void main(String args[]) {
        AplikasiToko frameku = new AplikasiToko();
        Boolean JDBC_Err = false;
        try {
            Class.forName(StringDriver);
        } catch (Exception ex) {
            JDBC_Err = true;
            JOptionPane.showMessageDialog(null,
                    "JDBC Driver tidak ditemukan atau rusak\n" + ex, "Kesalahan",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (!JDBC_Err) {
            try {
                Connection cn = DriverManager.getConnection(StringConnection);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Koneksi ke database Javatgs gagal\n" + ex, "Kesalahan",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}