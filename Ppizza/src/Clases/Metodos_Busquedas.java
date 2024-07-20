package Clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Metodos_Busquedas {
 ProductosDao proDao = new ProductosDao(); 
 
 
public void bubbleSort(List<Cliente> clientes) {
    int n = clientes.size(); 
    for (int i = 0; i < n - 1; i++) {
        // el primer bucle recorre desde el primer elemento hasta el penultimo
        for (int j = 0; j < n - i - 1; j++) {
            // el segundo bucle interno recorre desde el primer elemento hasta el ultimo
            if (clientes.get(j).getDni() > clientes.get(j + 1).getDni()) {
                // Compara el DNI de dos clientes adyacentes
                // Si el DNI del cliente en la posición j es mayor que el del siguiente (j + 1)
                // significa que están en el orden incorrecto y deben intercambiarse.
                //y se realiza el cambio
                Cliente temp = clientes.get(j);
                clientes.set(j, clientes.get(j + 1));
                clientes.set(j + 1, temp);
            }
        }
    }
}
//metodoshellSort
public void shellSortApelliPater(List<Cliente> clientes) {
    int n = clientes.size(); 
    int esp = n / 2; // Inicializa el espacio "esp" en la mitad de la lista
    
    while (esp > 0) {
        for (int i = esp; i < n; i++) {
            Cliente temp = clientes.get(i); // Almacena temporalmente el cliente en la posición "i"
            int j = i; // Inicializa j con la posición actual
            
            // Comienza la inserción en orden descendente utilizando el gap
            while (j >= esp && compareApellidoPaterno(clientes.get(j - esp), temp) < 0) {
                // Compara el apellido paterno del cliente en la posición j - esp con temp
                // Si el apellido del cliente en j - esp es menor que temp se desplaza el elemento hacia la derecha
                clientes.set(j, clientes.get(j - esp));
                j -= esp; // Se mueve hacia atrás en el espacio esp
            }
            
            clientes.set(j, temp); // Coloca el cliente 'temp' en la posición correcta en orden descendente
        }
        
        esp /= 2; // Reduce a la mitad el espacio "esp" en cada iteración
    }
}


private int compareApellidoPaterno(Cliente cliente1, Cliente cliente2) {
    // Obtiene el apellido paterno de cliente1 y lo convierte a minúsculas
    String apellido1 = cliente1.getPaterno().toLowerCase();

    // Obtiene el apellido paterno de "cliente2" y lo convierte a minúsculas
    String apellido2 = cliente2.getPaterno().toLowerCase();

    // Compara los apellidos paternos en orden descendente
    // el resultado de compareTo es negativo si "apellido2" es mayor que "apellido1"
    return apellido2.compareTo(apellido1);
}

//Metodo mergeSort
public void mergeSortTelefono(List<Cliente> clientes) {
    // Si la lista tiene 1 elemento o menos, está ordenada (caso base)
    if (clientes.size() <= 1) {
        return;
    }
    
    // Divide la lista en dos mitades
    int middle = clientes.size() / 2;
    List<Cliente> left = new ArrayList<>(clientes.subList(0, middle));
    List<Cliente> right = new ArrayList<>(clientes.subList(middle, clientes.size()));
    
    // Llama en ambas mitades
    mergeSortTelefono(left);
    mergeSortTelefono(right);
    
    // Fusiona las dos mitades ordenadas
    merge(clientes, left, right);
}


private void merge(List<Cliente> clientes, List<Cliente> izq, List<Cliente> dere) {
    int i = 0, j = 0, k = 0;
    
    
    while (i < izq.size() && j < dere.size()) {
        // Compara los elementos actuales de 'left' y 'right'
        if (izq.get(i).getTelefono() <= dere.get(j).getTelefono()) {
            // Si el elemento actual en izquierda es menor o igual al elemento actual en derecha,
            // coloca el elemento de izquierda en la posición 'k' de 'clientes'
            clientes.set(k++, izq.get(i++));
        } else {
            // Si el elemento actual en derecha es menor que el elemento actual en izquierda,
            // coloca el elemento de derecha en la posición k de Clientes
            clientes.set(k++, dere.get(j++));
        }
    }
    
    // Después de salir del bucle anterior puede que queden elementos en los 2 lados
    // Los siguientes dos bucles while manejan esos elementos restantes y los añaden a 'clientes'.

    while (i < izq.size()) {
        clientes.set(k++, izq.get(i++));
    }
    
    while (j < dere.size()) {
        clientes.set(k++, dere.get(j++));
    }
}
 public void ordenarPedidosPorTotalDescendente(List<Pedido> pedidos) { //Metodo de Selección
        int n = pedidos.size();
        for (int i = 0; i < n - 1; i++) {
            int k = i;
            Pedido menor = pedidos.get(i);

            for (int j = i + 1; j < n; j++) {
                if (pedidos.get(j).getTotal() > menor.getTotal()) {
                    k = j;
                    menor = pedidos.get(j);
                }
            }

            // Intercambiar los elementos en las posiciones i y k
            Pedido temp = pedidos.get(i);
            pedidos.set(i, pedidos.get(k));
            pedidos.set(k, temp);
        }
    }
 
 //Busqueda secuencial 
 public Producto buscarProductoPorCodigo(String codigo) {
    List<Producto> listaProductos = proDao.ListadoPro(); // Obtener la lista de productos desde proDao

    for (Producto producto : listaProductos) {
        if (producto.getCodigoP().equals(codigo)) {
            return producto; // Devuelve el producto si se encuentra
        }
    }

    return null; // Devuelve null si no se encontró el producto
}
 
 public void ordenarPorCantidadAscendente(List<Producto> listaProductos) {
    int n = listaProductos.size();
    
    for (int i = 1; i < n; i++) {
        Producto key = listaProductos.get(i);
        int j = i - 1;
        
        while (j >= 0 && listaProductos.get(j).getCantidad() > key.getCantidad()) {
            listaProductos.set(j + 1, listaProductos.get(j));
            j = j - 1;
        }
        
        listaProductos.set(j + 1, key);
    }
}
}
