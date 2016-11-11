#FloatingPrismView

###A A floating button which will expand/collapse to/from other buttons. The floating buttons will be arranged in a circle.

#Usage
##In Xml
```
  <com.anwesome.games.prisview.PrisView
      android:layout_width="300dp"
      android:layout_height="300dp"
      android:id="@+id/my_prism_view"/>

```
##In Activity(Creating the PrismView)
```
  PrismView prismView = (PrismView)findViewById(R.id.my_prism_view);
```
##Creating Prism Buttons
```
  PrismButton button1 = PrismButton.newInstance();
  PrismButton button2 = PrismButton.newInstance();
  PrismButton button3 = PrismButton.newInstance();
  PrismButton button4 = PrismButton.newInstance();
  ....
```
##Attaching the event listeners
```
  button1.setOnClickListener(new PrisButtonClickListener(){
      public void onClick(View view) {

      }
  });
  button2.setOnClickListener(new PrisButtonClickListener(){
      public void onClick(View view) {

      }
  });
  button3.setOnClickListener(new PrisButtonClickListener(){
      public void onClick(View view) {

      }
  });
  ...
```
##Adding buttons to views
```
  prismView.add(button1,button2,button3,....)
```

![image](https://github.com/Anwesh43/FloatingPrismView/screenshots/prismview.gif " PrismView")
![image](https://github.com/Anwesh43/FloatingPrismView/screenshots/prismviewwith8.gif "PrismView with 8 buttons")
