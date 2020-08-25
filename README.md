# WordFlow

(SEE THE RUSSIAN DESCRIPTION BELOW)

The program is a Java Swing implementation of a smart keyboard like SwiftKey from scratch.

The user draws a continuous line using the mouse or the touch screen, connecting all the letters of the word with each other, and the algorithm suggests the three most suitable results. After clicking on the proposed option, the word appears in the result window.

The keyboard color and line color can be changed, and the color of the keys automatically becomes contrast to the background color.

If the searched word is not in the dictionary, it can be added by entering it into a special text field (the program monitors the uniqueness of words in the dictionary).

The program uses a dictionary of the 5000 most frequent words of the Russian language. The dictionary is loaded asynchronously when the program starts. You can use another dictionary, it should be a .txt document and there should be only one word on each line. There is a "clear history" function that removes all custom words from the dictionary (to use it, the last line of the dictionary must consist of 5 hyphens: -----). Please remeber that the larger is the dictionary, the worse are the results of the algorithm work.

Since the panel saves all the letters which the line has been drawn through, the search for the most suitable options by the pattern includes 4 stages:
1. Looking for a word that totally matches the pattern.
2. Looking for words, the first and the last letters of which match the first and the last letters of the pattern.
3. The words got in the step 2 are sorted by how many letters are present in the template.
4. The words got in the step 3 are sorted by how many of their letters appear in the correct order in the pattern.

![github-small](https://sun9-21.userapi.com/MVY9MXMedUylTlLBWUngkA7_2EFkBsVO8NlPoQ/Eqd1FbTrm_4.jpg"1")<br><br>
![github-small](https://sun9-62.userapi.com/0hTXWAU5LpaC0mOj6otEaYPBoc_dAY0GVIahuA/eMA4lVqLoGM.jpg"2")<br><br>
![github-small](https://sun9-70.userapi.com/D-y7EJwgD2q7yp1qJufwOFDV8GlawMGTMrJcFg/b9dCFdfTpXI.jpg"3")<br><br>

Программа представляет собой реализацию на Java Swing с нуля умной клавиатуры наподобие SwiftKey. 

Пользователь с помощью мыши или сенсорного экрана рисует непрерывную линию, соединяя все буквы слова между собой, а алгоритм предлагает три наиболее подходящих результата. После нажатия на предложенный вариант слово появляется в окне результата. 

Цвет клавиатуры и цвет линии можно изменить, при этом цвет клавиш автоматически становится контрастным по отношению к цвету фона. 

Если нужного слова нет в словаре, оно может быть добавлено путём ввода в специальное текстовое поле (программа следит за уникальностью слов в словаре).

Программа использует словарь из 5000 самых частых слов русского языка. Словарь загружается асинхронно при запуске программы. Можно использовать другой словарь, это должен быть документ txt, на каждой строке должно быть только одно слово. Есть функция очистки истории, которая удаляет все пользовательские слова из словаря (для того, чтобы она работала, последней строкой словаря должны быть 5 дефисов: ----- ). Стоит отметить, что чем больше словарь, тем хуже результаты работы алгоритма.

Так как панель запоминает все буквы, через которые прошла линия, поиск наиболее подходящих вариантов по шаблону происходит в 4 этапа:
1. Ищется слово, полностью совпадающее с шаблоном.
2. Ищутся все слова, начальная и конечная буква которых совпадают с начальной и конечной буквой шаблона.
3. Слова, полученные в пункте 2, сортируются на основе того, какое количество их букв содержится в шаблоне.
4. Слова, полученные в пункте 3, сортируются на основе того, какое количество их букв идёт в нужном порядке в шаблоне. 
