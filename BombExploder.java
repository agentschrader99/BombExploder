//Yohann Manseau-Glémot (20217138) et Alex Chevrier (20216495)

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class BombExploder {

    //Les deux variables ci-dessous vont stocker les dimensions des matrices
    
    public static int ligne;
    public static int col;

    //Nous nous servons d'une ArrayList pour stocker les valeurs des matrices

    public static ArrayList<Integer> matrice = new ArrayList<Integer>();

    //Nous utilisons une queue pour gerer l'alogithme du jeu

    public static Queue<Integer> queue = new PriorityQueue<Integer> ();
    
    //Méthode principal main qui créer un scanner pour lire dans le fichier passé en paramètre. Le fichier
    //correspond à args[0]
    public static void main(String[] args) throws Exception {

        //Scanner pour lire les données d'un fichier passé en paramètre (args[0])

        Scanner scanner = new Scanner(new File(args[0]));

        //Tant que le fichier n'est pas vide

        while(scanner.hasNext()){

            //On stock les deux premiers chiffres du fichier qui sont les dimensions de la première matrice

            ligne = scanner.nextInt();
            col = scanner.nextInt();
           
            //Sachant les dimensions ligne et col de la matrice nous savons combien d'éléments elle contiendra alors
            //on scan les ligne*col prochains chiffres et on les stock dans la matrice

            for(int i = 0; i< ligne*col; i++){

                matrice.add(scanner.nextInt());
            }
            
            //On revisite les éléments de la matrice et si chiffre vaut 2 alors on l'ajoute à la queue

            for(int y =0; y< matrice.size(); y++){

                if (matrice.get(y) == 2){
                    queue.add(y);
                }           
            }
            
            
            //On utilise la méthode algo pour calculer le nombre d'itérations nécéssaire pour faire exploser toutes les bombes

            int resultat = algo();

            //On imprime le résultat sans ligne blanche avant ou après
            if (scanner.hasNext() == false){
                System.out.print(resultat);
            }
            else
                System.out.println(resultat);
                

            //On efface la dernière matrice et la boucle while recommence si il y a une autre matrice à analyser

            matrice.removeAll(matrice);  
        }    

    }
    
    //Déclaration de la méthode algo qui calcul le nombre d'itérations pour compléter le jeu
   //Note: les méthodes add et remove sont l'équivalent de enqueue et dequeue

    public static int algo(){
        
        //On stock le nombre d'itération

        int iteration = 0;
        
        //Tant que la file n'est pas vide

        while(!queue.isEmpty()){

            //On incrémente le nombre d'itération après la fin de chaque boucle for

            iteration++;
            
            //On stock la size de la queue à chaque itération de la boucle while
            //et on creer un tableau de cette grosseur

            int size = queue.size();
            int[] tab = new int[size];

            //On stock tous les éléments de la queue dans ce tableau

            for(int d =0; d<size;d++){

                tab[d] = queue.remove();
            }
            
            //On boucle sur la grandeur de la file

            for(int j = 0; j<size; j++){

                //la variable indice contient l'indice ou se trouve un 2 dans la ArrayList matrice

                int indice = tab[j];
                
                //Les variables ci-dessous correspond aux positions adjacentes d'un élément d'une matrice dans l'ArrayList

                int haut = indice - BombExploder.col;
                int bas = indice + BombExploder.col;
                int droite = indice + 1;
                int gauche = indice - 1;
            
                //Donc pour chaque indice d'un 2 dans la file, on vérifie si les chiffres à ses positions adjacentes sont des 1
                //Si c'est des 1 alors on modifie le chiffre à la position adjacente dans la matrice par 2 et on ajoute cet indice à la queue
                //et on recommence le processus
                //La partie a droite du && s'assure que le 1 est dans les bornes de la matrice
                //La partie a droite du && (pour les deux premiers if) empeche un 2 de toucher un 1 à gauche de la ligne en dessous du 2

                if((droite < matrice.size()) && ((indice+1)%col != 0)){

                    if(matrice.get(droite) == 1){

                        matrice.set(droite, 2);
                        queue.add(droite);
                        
                    }         
                }

                if((gauche>= 0) && (indice%col != 0)){

                    if (matrice.get(gauche) == 1 ){
                        
                        matrice.set(gauche, 2);
                        queue.add(gauche);
                        
                    }   
                } 

                if(haut >= 0){

                    if(matrice.get(haut) == 1){

                        matrice.set(haut, 2);
                        queue.add(haut);
                        
                    }   
                }

                //Par exemple ici le if signifie, si l'élément en bas d'un 2 se situe dans les bornes de la matrice et qu'il vaut 1 alors...

                if(bas < matrice.size()){

                    if(matrice.get(bas) == 1){

                        matrice.set(bas, 2);
                        queue.add(bas);
                        
                    }
                }   
            }         
        }

        //On vérifie si la matrice finale contient des 1, si oui on retourne -1
        for(int k = 0; k< matrice.size();k++){

            if(matrice.get(k) == 1)

                return -1;
                
        }

        //Sinon on retourne le nombre d'itérations;
        return iteration;
    }   
}

