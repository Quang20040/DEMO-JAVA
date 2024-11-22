/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pizzastorect467;

/**
 *
 * @author trant
 */
public class NhanVien {
    private int ID_NV;
    private String HoTen;
    private String SoDienThoai;
    private String NgayTaoHoSo;
    private int GioiTinh;
    private int BiDuoiViec;

    public NhanVien()
     {  
        this.HoTen = new String();
        this.SoDienThoai= new String();
        this.NgayTaoHoSo = new String();
        this.GioiTinh = 0;
        this.BiDuoiViec = 0;

     }

    public NhanVien(int ID_NV, String HoTen, String SoDienThoai, String NgayTaoHoSo, int GioiTinh, int BiDuoiViec) {
        this.ID_NV = ID_NV;
        this.HoTen = HoTen;
        this.SoDienThoai = SoDienThoai;
        this.NgayTaoHoSo = NgayTaoHoSo;
        this.GioiTinh = GioiTinh;
        this.BiDuoiViec = BiDuoiViec;
    }
    
     public int getID_NV() {
        return ID_NV;
    }

    public void setID_NV(int ID_NV) {
        this.ID_NV = ID_NV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getNgayTaoHoSo() {
        return NgayTaoHoSo;
    }

    public void setNgayTaoHoSo(String NgayTaoHoSo) {
        this.NgayTaoHoSo = NgayTaoHoSo;
    }

  

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public int getBiDuoiViec() {
        return BiDuoiViec;
    }

    public void setBiDuoiViec(int BiDuoiViec) {
        this.BiDuoiViec = BiDuoiViec;
    }

   
    
    
}
