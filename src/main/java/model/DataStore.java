package model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    // Instancia estática y única de la clase
    private static DataStore instancia;

    // Nuestra "base de datos" en memoria
    private List<Cliente> listaClientes;

    // Constructor privado para evitar que la instancien desde afuera
    private DataStore() {
        listaClientes = new ArrayList<>();
    }

    // Método para obtener la única instancia de la clase
    public static DataStore getInstancia() {
        if (instancia == null) {
            instancia = new DataStore();
        }
        return instancia;
    }

    // Métodos para interactuar con la lista
    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}