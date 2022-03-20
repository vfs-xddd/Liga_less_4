package ru.digitalleague;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.digitalleague.categories.Negative;
import ru.digitalleague.categories.Positive;

@RunWith(Categories.class)
@Categories.ExcludeCategory (Negative.class)
@Suite.SuiteClasses( { MyTests.class})
public class MyTestsSuite {
}
