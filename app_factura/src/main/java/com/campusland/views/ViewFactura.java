package com.campusland.views;

import java.time.LocalDateTime;

import com.campusland.exceptiones.clienteexceptions.ClienteNullException;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.respository.models.ItemFactura;
import com.campusland.respository.models.Producto;
import com.campusland.utils.ConexionBDList;

public class ViewFactura extends ViewMain{


    public static void startMenu() {

        int op = 0;

        do {

            op = mostrarMenu();
            switch (op) {
                case 1:
                    crearFactura();
                    break;
                case 2:
                    listarFactura();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 3);

    }


    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear factura.");
        System.out.println("2. Listar factura.");      
        System.out.println("3. Salir ");
        return leer.nextInt();
    }

    public static void listarFactura() {
        System.out.println("Lista de Facturas");
        for (Factura factura : serviceFactura.listar()) {
            factura.display();
            System.out.println();
        }
    }

    public static void crearFactura() {
        leer.nextLine();
        System.out.println("Ingrese documento del cliente: ");
        String documento = leer.nextLine();

        Cliente newCliente = validarCliente(documento);
        Factura newFactura = new Factura(LocalDateTime.now(), newCliente);
        int sel = 0;

        do {
            menuCompra();
            sel = leer.nextInt();
            switch (sel) {    
                case 1:
                    System.out.println("Ingrese el código del producto: ");
                    Producto prod = validarProducto(leer.nextInt());
                    if (prod == null) {
                        System.out.println("El código ingresado no se encontró");
                        System.out.println();
                        continue;
                    }
                    else {
                        System.out.println("¿Cantidad del producto?");
                        newFactura.agregarItem(new ItemFactura(leer.nextInt(),prod));
                        System.out.println("Producto agregado con exito!...");
                        System.out.println();
                        mostrarFactura(newFactura);
                    }
                    break;
                
                case 2:
                    System.out.println();
                    for (Producto producto : serviceProducto.listar()) {
                        System.out.println("Código: " + producto.getCodigo());
                        System.out.println("Producto: " + producto.getNombre());
                        System.out.println();
                    }
                    break;

                case 99:
                    serviceFactura.crear(newFactura);
                    System.out.println("Gracias por su compra.");
                    mostrarFactura(newFactura);
                    break;
            }
            

            
        }
        while(sel != 99);

    }

    private static void mostrarFactura(Factura fact) {
        System.out.println();
        fact.display();
        System.out.println();
    }

    private static Cliente validarCliente(String documento) {
        try {
            return serviceCliente.porDocumento(documento);
        } catch (ClienteNullException e) {
            System.out.println("Ingrese nombre del cliente: ");
            String nombre = leer.nextLine();
            System.out.println("Ingrese apellido del cliente: ");
            String apellido = leer.nextLine();
            System.out.println("Ingrese email del cliente: ");
            String email = leer.nextLine();
            System.out.println("Ingrese dirección del cliente: ");
            String direccion = leer.nextLine();
            System.out.println("Ingrese celular del cliente: ");
            String celular = leer.nextLine();
            Cliente creado = new Cliente(nombre,apellido,email,direccion,celular,documento);
            serviceCliente.crear(creado);
            return creado;
        }
    }

    private static void menuCompra() {
        System.out.println("------------------------------------------");
        System.out.println("1) Ingresar código producto");
        System.out.println("2) Listar productos");
        System.out.println("99) Salir");
        System.out.println("------------------------------------------");
    }

    private static Producto validarProducto(int cod) {
        try {
            return serviceProducto.porCodigo(cod);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    
}
