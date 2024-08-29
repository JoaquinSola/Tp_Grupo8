/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.tp.grupo8;

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
    public static void main(String[] args) {

//VENDEDOR----------------------   
        //coordenadas vendedor
        Coordenada coorV1 = new Coordenada (3.18f,7.47f);
        Coordenada coorV2 = new Coordenada (1.25f,4.89f);
        Coordenada coorV3 = new Coordenada (6.59f,12.1f);
        //vendedores
        Vendedor v1 = new Vendedor ("v00001", "Horacio", "Iriondo 1582", coorV1);
        Vendedor v2 = new Vendedor ("v00002", "Marcelo", "Misiones 492", coorV2);
        Vendedor v3 = new Vendedor ("v00003", "Florencia", "Santa Fe 2123", coorV3);
        //vector de vendedores
        Vendedor[] vendedores = new Vendedor[3];
        vendedores[0] = v1;
        vendedores[1] = v2;
        vendedores[2] = v3;
        //busqueda por nombre o id
        //buscarVendedor(vendedores,"Marcelo");
        buscarVendedor(vendedores, "Florencia");
        System.out.println("\n");
        vendedores = eliminarVendedor(vendedores, buscarVendedor(vendedores,"Marcelo"));
        //CLIENTE----------------------        
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
        buscarCliente(clientes, "c00003");
        System.out.println("\n");
        //busqueda por id
        clientes= eliminarCliente(clientes,buscarCliente(clientes,"c00001"));
        System.out.println("Notebook Joa desde VsCode");
    }
   
}
