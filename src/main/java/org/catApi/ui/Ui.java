package org.catApi.ui;

import org.catApi.api.CatApi;
import org.catApi.model.FavoriteCatModel;
import org.catApi.model.SeeCatModel;
import org.catApi.service.CatService;
import javax.swing.JOptionPane;

public class Ui {

    public static void mainMenu() {

        final String[] OPTIONS = {"See Cats", "See Favorites", "Exit"};
        int response = 0;
        do{
            //Desplegamos el menu principal
            String option = (String) JOptionPane.showInputDialog(null,
                    "( =①ω①=)",
                    "Cat Menu",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    OPTIONS,
                    OPTIONS[0]);

            switch (option){
                case "See Cats":
                    response = 1;
                    SeeCatModel seeCatModel = CatService.seeCatService();
                    seeCatMenu(seeCatModel);
                    break;
                case "See Favorites":
                    FavoriteCatModel[] favoriteCatModels = CatApi.getApiFavoriteCats();
                    if(favoriteCatModels.length > 0){
                        favoriteCatMenu(favoriteCatModels);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "There Are No Images in Favorites");
                    }
                    break;
                case "Exit":
                    response = 1;
                    JOptionPane.showMessageDialog(null,
                            "MIAU (=^･ｪ･^=) GOOD BYE!");
                    break;
                default:
                    System.out.println("MainMEnu: Incorrect Option");
            }
        }while (response == 0);
    }

    public static void seeCatMenu(SeeCatModel seeCatModel){

        final String[] OPTIONS = {"See other image", "Add to Favorites", "Return to Main Menu"};
        int response = 0;
        do {
            String option = (String) JOptionPane.showInputDialog(null,
                    "(=^･ｪ･^=)",
                    seeCatModel.getId(),
                    JOptionPane.INFORMATION_MESSAGE,
                    seeCatModel.getImage(),
                    OPTIONS,
                    OPTIONS[0]);

            switch (option) {
                case "See other image":
                    response = 1;
                    SeeCatModel seeCatModel2 = CatService.seeCatService();
                    seeCatMenu(seeCatModel2);
                    break;
                case "Add to Favorites":
                    if (CatApi.postApiAddFavoriteCat(seeCatModel.getId())) {
                        System.out.println("Image Added Successfully");
                    } else {
                        System.out.println("The Cat Could Not Be Added");
                    }
                    break;
                case "Return to Main Menu":
                    response = 1;
                    mainMenu();
                    break;
                default:
                    System.out.println("CatMenu: Incorrect Option");
            }
        }while (response == 0);
    }

    public static void favoriteCatMenu(FavoriteCatModel[] favoriteCatModels){

        if(favoriteCatModels.length > 0){
            final String[] OPTIONS = {"See other image", "Delete Favorite", "Return to Main Menu"};

            for (FavoriteCatModel favoriteCatModel : favoriteCatModels) {
                if(CatService.resizeImageService(favoriteCatModel.getImage().getUrl()) != null){
                    String option = (String) JOptionPane.showInputDialog(null,
                            "(=^･ｪ･^=)",
                            favoriteCatModel.getId(),
                            JOptionPane.INFORMATION_MESSAGE,
                            //Obtenemos la imagen de la url
                            CatService.resizeImageService(favoriteCatModel.getImage().getUrl()),
                            OPTIONS,
                            OPTIONS[0]);

                    switch (option) {
                        case "See other image":
                            break;
                        case "Delete Favorite":
                            if(CatApi.deleteApiFavoriteCat(favoriteCatModel.getId())){
                                System.out.println("Cat Deleted Successfully");
                            }else {
                                System.out.println("The Cat Could Not Be Deleted");
                            }
                            break;
                        case "Return to Main Menu":
                            mainMenu();
                            break;
                        default:
                            System.out.println("CatMenu: Incorrect Option");
                    }
                }
            }//for
        } else {
            JOptionPane.showMessageDialog(null,"There Are No Images in Favorites");
        }
    }
}
