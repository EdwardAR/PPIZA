/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaz;

/**
 *
 * @author Drinksmile
 */
//tipo generico 
public interface CRUD <T>{
    public boolean Registrar(T c);
    public boolean modificar(T c);
}
