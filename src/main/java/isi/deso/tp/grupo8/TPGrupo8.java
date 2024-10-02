
package isi.deso.tp.grupo8;


import java.util.HashSet;
import java.util.Set;

public class TPGrupo8 {
    public static Vendedor [] eliminarVendedor(Vendedor [] v, Vendedor vAEliminar){
        int i = 0;
        int k=0;
        int tam = v.length;
        while(i < tam){
            if(v[i] ==  vAEliminar){
                System.out.println("\n");
                System.out.println("El vendedor: "+ v[i].getNombre()+" ha sido eliminado \n");
                tam--;
                for(int j = i; j < tam; j++){
                    v[j] = v[j+1];
                     
                }
            }
        i++;
        }
        Vendedor[] nuevoArreglo = new Vendedor[tam];
         while(k < tam){
             nuevoArreglo[k] = v[k];
             k++;
            }
         return nuevoArreglo;
         }
       
    public static Vendedor buscarVendedor(Vendedor [] v, String nombreOid){
        int i = 0;
        while(i < v.length){
          if(((v[i].getId()).equals(nombreOid)) || ((v[i].getNombre()).equals(nombreOid))){
            System.out.println("ID del vendedor: "+ v[i].getId()+"\n"
                    + "Nombre del Vendedor: " +v[i].getNombre()+"\n"
                    + "Direccion del vendedor: "+ v[i].getDireccion()+"\n"
                    + "Coordenadas de latitud "+ (v[i].getCoor()).getLatitud()+"\n" 
                    + "Coordenadas de Longitud: "+(v[i].getCoor()).getLongitud());
                    return v[i];
                    //i = v.length;
            }
          i++;
        }
        if(i == v.length) System.out.println("No existe el VENDEDOR"); 
             return null;
        
    }
    
    public static Cliente [] eliminarCliente(Cliente [] v, Cliente cAEliminar){
        int i = 0;
        int k=0;
        int tam = v.length;
        while(i < tam){
            if(v[i] ==  cAEliminar){
                System.out.println("\n");
                System.out.println("El cliente: "+ v[i].getId()+" ha sido eliminado \n");
                tam--;
                for(int j = i; j < tam; j++){
                    v[j] = v[j+1];
                }
            }
        i++;
        }
        Cliente[] nuevoArreglo = new Cliente[tam];
         while(k < tam){
             nuevoArreglo[k] = v[k];
             k++;
            }
         return nuevoArreglo;
         }
    
    public static Cliente buscarCliente(Cliente [] v, String id){
        int i = 0;
        while(i < v.length){
          if(((v[i].getId()).equals(id)) || ((v[i].getId()).equals(id))){
            System.out.println("ID del cliente: "+ v[i].getId()+"\n"
                    + "CUIT del cliente: "+ v[i].getCuit()+"\n"
                    + "Email del cliente: "+ v[i].getEmail()+"\n"
                    + "Direccion del cliente: "+ v[i].getDireccion()+"\n"
                    + "Coordenadas de latitud "+ (v[i].getCoor()).getLatitud()+"\n" 
                    + "Coordenadas de Longitud: "+(v[i].getCoor()).getLongitud());
                    return v[i];
                    //i = v.length;
            }
          i++;
        }
        if(i == v.length) System.out.println("No existe el CLIENTE"); 
             return null;
        
    }
    public static void main(String[] args) throws ItemNoEncontradoException {
        // INSTANCIA 1
        //-----------------------------VENDEDOR------------------------------------------------------
        //coordenadas vendedor
        Coordenada coorV1 = new Coordenada (3.18f,7.47f);
        Coordenada coorV2 = new Coordenada (1.25f,4.89f); //LATITUD LONGITUD
        Coordenada coorV3 = new Coordenada (6.59f,12.1f);
        //Lista
        Set<ItemMenu> productos = new HashSet<>();
        Set<ItemMenu> productos2 = new HashSet<>();
        Set<ItemMenu> productos3 = new HashSet<>();
        Set<ItemMenu> productos4 = new HashSet<>();
        //vendedores
        Vendedor v1 = new Vendedor ("v00001", "Horacio", "Iriondo 1582", coorV1,productos);  //EL ULTIMO ATRUBUTO ES LA LISTA DE PRODUCTOS ASOCIADO A ESE VENDEDOR
        Vendedor v2 = new Vendedor ("v00002", "Marcelo", "Misiones 492", coorV2,productos2);
        Vendedor v3 = new Vendedor ("v00003", "Florencia", "Santa Fe 2123", coorV3,productos3);
        Vendedor v4 = new Vendedor ("v00004", "Rodrigo", "Tacural 2123", coorV3,productos4);
        //vector de vendedores
        Vendedor[] vendedores = new Vendedor[4];
        vendedores[0] = v1;
        vendedores[1] = v2;
        vendedores[2] = v3;
        vendedores[3] = v4;
        //busqueda por nombre o id
        //buscarVendedor(vendedores,"Marcelo");
        buscarVendedor(vendedores, "Florencia");
        System.out.println("\n");
        vendedores = eliminarVendedor(vendedores, buscarVendedor(vendedores,"Marcelo"));
        //---------------------------------------------CLIENTE----------------------------------------------  
        //coordenadas cliente
        Coordenada coorC1 = new Coordenada (45.3f,99.3f);
        Coordenada coorC2 = new Coordenada (18.9f,78.6f);
        Coordenada coorC3 = new Coordenada (65.36f,91.5f);
        //clientes
        Cliente c1 = new Cliente ("c00001", "20-30495842-7", "jAlvarez@gmail.com","San Martin 1523", coorC1);
        Cliente c2 = new Cliente ("c00002", "27-15378964-3", "juanPerez25@outlook.es","9 de Julio 2876", coorC2);
        Cliente c3 = new Cliente ("c00003", "20-27894561-9", "lopezLopezCarlos@hotmail.com"," Gobernador Crespo 4201", coorC3);
       //vector de clientes
        Cliente[] clientes = new Cliente[3];
        clientes[0] = c1;
        clientes[1] = c2;
        clientes[2] = c3;
        System.out.println("\n");
        //buscar clientes
        buscarCliente(clientes, "c00003");
        System.out.println("\n");
        //eliminar cliente por id
        clientes= eliminarCliente(clientes,buscarCliente(clientes,"c00001"));
        //imprimir distancia en km
        System.out.println("La distancia es de: "+v2.distancia(c2)+"Km");

        //INSTANCIA 2
        Plato plato1 = new Plato(15,false,false,"Bife",15);
        Plato plato2 = new Plato(15,true,true,"Lechuga",69);
        Plato plato3 = new Plato(23,true,false,"Pizza",12);
        Plato plato4 = new Plato(18,false,true,"Wok de verduras",225.5);
        Plato plato5 = new Plato(20,false,false,"Alfajor",10);
        Plato plato6 = new Plato(36,false,false,"Papa Frita",39);
        Bebida bebida1 = new Bebida(40, 450,"Vodka");
        Bebida bebida2 = new Bebida(0, 450,"Manaos");
        Bebida bebida3 = new Bebida(0, 500,"Limonada");
        Bebida bebida4 = new Bebida(39,750,"Fernet con Coca");
        Bebida bebida5 = new Bebida(0,750,"Agua Tonica");
        Bebida bebida6 = new Bebida(0,500,"Pepsi");

        //Productos asignados a Vendedor 1
        productos.add(bebida1);
        productos.add(bebida2);
        productos.add(plato1);
        productos.add(plato2);
        //Productos asignados a Vendedor 2
        productos2.add(bebida3);
        productos2.add(bebida4);
        productos2.add(plato3);
        productos2.add(plato4);
        //Productos asignados a Vendedor 4
        productos4.add(bebida5);
        productos4.add(bebida6);
        productos4.add(plato5);
        productos4.add(plato6);

        System.out.println("El peso del plato es: "+plato1.peso(15));
        plato1.esComida();
        plato1.esBebida();
        System.out.println("El peso de la bebida es: "+bebida1.peso(15));
        bebida1.esComida();
        bebida1.esBebida();
        System.out.println("Comidas asociadas al vendedor "+v2.getNombre()+": "+v2.getComidas()); //IMPRIME LISTA DE COMIDAS QUE VENDE ESE VENDEDOR
        System.out.println("Bebibdas asociadas al vendedor "+v2.getNombre()+": "+v2.getBebidas()); //IMPRIME LISTA DE BEBIDAS QUE VENDE ESE VENDEDOR
        v2.mostrarProductos();                                                      //IMPRIME LISTA TOTAL QUE VENDE ESE VENDEDOR
        v2.bebidaSinAlcohol();                                                      //IMPRIME LISTA DE BEBIDAS SIN ALCOHOL QUE VENDE ESE VENDEDOR
        v2.comidaVegana();                                                          //IMPRIME LISTA DE COMIDA VEGANA QUE VENDE ESE VENDEDOR

        //INSTANCIA 3
        ItemPedido p1 = new ItemPedido(plato1);
        ItemPedido p2 = new ItemPedido(plato2);
        ItemPedido p3 = new ItemPedido(plato3);
        ItemPedido p4 = new ItemPedido(plato4);
        ItemPedido p5 = new ItemPedido(plato5);
        ItemPedido p6 = new ItemPedido(plato6);
        ItemPedido b1 = new ItemPedido(bebida1);
        ItemPedido b2 = new ItemPedido(bebida2);
        ItemPedido b3 = new ItemPedido(bebida3);
        ItemPedido b4 = new ItemPedido(bebida4);
        ItemPedido b5 = new ItemPedido(bebida5);
        ItemPedido b6 = new ItemPedido(bebida6);
        
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 1
        ItemsPedidoMemory ipm1 = new ItemsPedidoMemory();
        ipm1.agregarItem(p1);
        ipm1.agregarItem(p2);
        ipm1.agregarItem(b1);
        ipm1.agregarItem(b2);
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 2
        ItemsPedidoMemory ipm2 = new ItemsPedidoMemory();
        ipm2.agregarItem(p3);
        ipm2.agregarItem(p4);
        ipm2.agregarItem(b3);
        ipm2.agregarItem(b4);
        //PEDIDO QUE SE ASIGNA AL VENDEDOR 4
        ItemsPedidoMemory ipm4 = new ItemsPedidoMemory();
        ipm4.agregarItem(p5);
        ipm4.agregarItem(p6);
        ipm4.agregarItem(b5);
        ipm4.agregarItem(b6);
        Pedido ped1 = new Pedido("1",c1, ipm1,v1);
        Pedido ped2 = new Pedido("2",c2, ipm2,v2);
        Pedido ped3 = new Pedido("3",c3, ipm4,v4);
        ipm1.busquedaPorRangodePecios(0, 700);
        //v2.mostrarProductos(); // EJEMPLO, ESTE VENDEDOR TIENE MUCHOS ITEMS A SU VENTA
        ipm2.ordenarPorCriterio(); // ORDENA ALFABETICAMENTE
         //en el argumento se coloca el tipo de plato (Comida, Bebida, Comida Vegetariana y bebidas Alcoholicas), y filtra por aquellas que cumplen la condicion
        ipm2.filtrado('C');  // filtra por COMIDAS
        ipm2.filtrado('A'); //filtra por BEBIDAS ALCOHOLICAS
        ipm2.filtrado('B'); //filtra por BEBIDAS
        ipm2.filtrado('V'); //filtra por COMIDA VEGANA
        ipm2.buscarPorRestaurante(v2); // BUSCA QUE VENDEDOR, CUMPLE CON TODOS LOS ITEMS A PROVEER , ESTOS ITEMS SON LOS QUE ESTAN DENTRO DE DE LA INSTANCIA DE ItemsPedidosMemory

        //Compruebo si el estado cambia -----OK!
        ped2.pagarConMP("JoaquinSola.MP");
        ped1.estadoDelPedido();
        ped2.estadoDelPedido();
        

        
    }  
   
}
