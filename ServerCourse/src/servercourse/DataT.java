/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercourse;

abstract public class DataT {
    protected java.sql.Statement stat;
    protected DBConnection DB;

    public DataT(java.sql.Statement stat, DBConnection DB) {
        this.stat = stat;
        this.DB = DB;
    }

    public String quotate(String content) {
        return "'" + content + "'";
    }
}
