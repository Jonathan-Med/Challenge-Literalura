<h1 style="text-align: center;"> Challenge Literalura</h1>
<p style="text-align: center;">Este es un programa backend en el que se pueden consultar libros y autores, para poder llenar la base de datos tuve que realizar  
la conexión con la API de Gutendex. De esa forma realizamos solicitudes a la API para poder llenar de información nuestra base  
de datos, que en este caso fue hecha con PostgreSQL. además de eso, el programa tiene varias opciones con las que el usuario  
puede interactuar. </p>    
<h2 style="text-align: center;">Tecnologías usadas:</h2>  
<p style="text-align: center;">
  <ul>
    <li>Lenguaje de Programación: Java</li>
    <li>Framework: Spring boot</li>
    <li>Base de datos: PostgreSQL</li>
    <li>Gestor de dependencias: Maven</li>
    <li>ORM: Hibernate</li>
  </ul>
</p>  

<p style="text-align: center;">Este programa fue hecho para el challenge de Literalura por parte del programa ONE de Alura Latam y Oracle.</p>  

<h3 style="text-align: center;">Estrucura</h3>
<p style="text-align: center;">A nivel estructural de archivos, el programa consta con el archivo de Model, Service, Principal y repository.  
<ul>
  <li>Model: contiene los modelos, es decir los records que se encargan de contener la información traducida del json y las clases Libro y Autor  
  que son las que se encargan de interactuar con la base de datos y el usuario.</li>
  <li>Service: Aquí se encuentran los metods que se encargan de obtener el json y traducir los daots de la API.</li>
  <li>Principal: Aquí se encuentra el menú y todo el codigo con el que puede interactuar el usuario.</li>
  <li>Repository: aquí se encuentra las interfaces que van a realizar las consultar mediante JPA, JPQL e Hibernate hacia la Base de datos.</li>
</ul>
</p>
    
<h3 style="text-align: center;">Menú</h3>  
<p style="text-align: center;">La aplicación tiene varias funcionalidades, la primera es para traer un libro y autor (si no están registrados) de la API a la base de datos.  
La opcion 2 y 4 muestran todos los libros y autores de la base de datos, respectivamente, la opción 3 y 7 muestra solo el libro o autor por su titulo o nombre.  
La opción 5 hace una busqueda de autores dentro de un rango de años. en la opción 6 podemos buscar un libro deacuerdo a su idioma.  
La opción 8 muestra un libro aleatorio que tenga más de cierto numero de descargas, en este caso 1000.  
En la opción 9 se despliegan todos los autores y se puede elegir uno para mostrar todos los libros de él registrados.  
Por ultimo el top 10 muestra los 10 libros con más descargas.  
  
El programa cuenta con manejo de excepciones, ya sea de entradas de letras en donde debía ser un número, números fuera de rango o incluso entradas vacías.  
Acontinucación unos ejemplos:  </p>




