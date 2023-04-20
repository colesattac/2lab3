package kz.talipovsn.table;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Количество строк в таблице
    private final int rows = 4;
    // Количество столбцов в таблице
    private final int columns = 5;
    // Массив для работы с данными таблицы
    int[][] arr = new int[rows][columns];
    // Объявляем таблицу
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.tableLayout1);
        // Заполняем массив нулями
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arr[i][j] = 0;
            }
        }
        // Параметры разметки таблицы
        table.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        table.setStretchAllColumns(true);
        // Создание таблицы
        createTable(rows, columns, arr);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Сохраняем данные при повороте экрана
        outState.putIntArray("matrix5x0", arr[0]);
        outState.putIntArray("matrix5x1", arr[1]);
        outState.putIntArray("matrix5x2", arr[2]);
        outState.putIntArray("matrix5x3", arr[3]);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Возвращаем данные в массив
        arr[0] = savedInstanceState.getIntArray("matrix5x0");
        arr[1] = savedInstanceState.getIntArray("matrix5x1");
        arr[2] = savedInstanceState.getIntArray("matrix5x2");
        arr[3] = savedInstanceState.getIntArray("matrix5x3");
        // Пересоздаем таблицу
        createTable(rows, columns, arr);
    }

    // Метод создания таблицы. Входные значения: количество строк, количество столбцов, двумерный массив с данными.
    public void createTable(int rows, int columns, int[][] arr) {
        // Удаляем предыдущие записи при пересоздании
        table.removeAllViews();

        for (int i = 0; i < rows; i++) {
            // Объявление строки
            TableRow tableRow = new TableRow(this);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(params);
            TableRow.LayoutParams param = new TableRow.LayoutParams();

            for (int j = 0; j < columns; j++) {
                // Объявление объекта строки
                int randvalue = arr[i][j];
                EditText value = new EditText(this);
                // Задаем текст объекту
                value.setText(String.valueOf(randvalue));
                value.setLayoutParams(param);
                // Размер Шрифта
                value.setTextSize(18);
                // По центру
                value.setGravity(Gravity.CENTER);
                // "Рамка таблицы"
                value.setBackgroundResource(R.drawable.back);
                // Отступы
                value.setPadding(5, 5, 5, 5);
                // Добавляем объект в строку
                tableRow.addView(value, j);
            }
            table.addView(tableRow, i);
        }
    }

    public void fillRand(View v) {
        // Заполняем массив числами от 0 до 100
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arr[i][j] = (int) Math.round(Math.random() * 100);
            }
        }
        // Пересоздание таблицы
        createTable(rows, columns, arr);
    }

    public void solveArr(View v) {
        // Записываем в массив значения таблицы
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                EditText value = (EditText) row.getChildAt(j);
                try {
                    arr[i][j] = Integer.parseInt(value.getText().toString());
                } catch (Exception e) {
                    // Вывод сообщения при исключении
                    Toast toast = Toast.makeText(getApplicationContext(), "Неправильно введенны данные!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        // Находим максмимальный элемент таблицы
        int startCell;
        int count = 0;
        startCell = arr[0][0];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int cl = arr[j][i];
                if (cl == 0) {
                    count += 1;
                }
            }

        }
        System.out.print(startCell);
        // Подсчитать количество нулей в таблице и заменить на это значение все нечётные целые элементы таблицы
        if (startCell == startCell) {
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    int cl = arr[j][i];
                    if (cl % 2 == 0) {
                        arr[j][i] = count;
                    }
                }
            }
        }

        Toast toast = Toast.makeText(getApplicationContext(), "Count: " + count, Toast.LENGTH_SHORT);
        toast.show();

        // Пересоздаем таблицу
        createTable(rows, columns, arr);
    }

}
