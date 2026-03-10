package org.example.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.example.domain.Entity;
import org.example.service.Service;
import org.example.utils.Observer;

/**
 * Clasă abstractă de bază pentru controllerele interfeței grafice (JavaFX).
 * Implementează interfața Observer pentru a putea reacționa la schimbările de stare
 * din straturile inferioare (ex: adăugarea/ștergerea unei entități prin Service).
 * Oferă, de asemenea, metode utilitare comune pentru afișarea mesajelor de tip pop-up.
 */
public abstract class AbstractController implements Observer
{

    /**
     * Metodă apelată automat (prin pattern-ul Observer) atunci când modelul de date se modifică.
     * De regulă, această metodă va apela la rândul ei initData() pentru a reîncărca informațiile pe ecran.
     */
    @Override
    public abstract void update();

    /**
     * Metodă abstractă destinată inițializării și populării elementelor vizuale (ex: TableView-uri,
     * combobox-uri) cu datele inițiale din baza de date sau la un eveniment de refresh.
     */
    public abstract void initData();

    /**
     * Metodă utilitară pentru afișarea unui dialog de eroare (Alert.AlertType.ERROR) către utilizator.
     * Blochează execuția (showAndWait) până când utilizatorul închide fereastra.
     * * @param text Mesajul de eroare care va fi afișat în corpul dialogului.
     */
    protected void showErrorMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    /**
     * Metodă utilitară pentru afișarea unui dialog de informare sau succes (Alert.AlertType.INFORMATION).
     * Blochează execuția (showAndWait) până când utilizatorul închide fereastra.
     * * @param text Mesajul informativ care va fi afișat în corpul dialogului.
     */
    protected void showInfoMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Info");
        message.setContentText(text);
        message.showAndWait();
    }
}