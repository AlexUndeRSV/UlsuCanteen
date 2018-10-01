package com.lynx.formi.ulsucanteen.presentation.menu.categories;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;
import com.lynx.formi.ulsucanteen.other.itemdecorators.GridItemDecorator;
import com.lynx.formi.ulsucanteen.presentation.menu.categories.adapter.CategoryAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CategoriesFragment extends MvpAppCompatFragment implements CategoriesView, CategoryAdapter.OnCategoryClickListener, TitleProvider {
    public static final String TAG = "CategoriesFragment";
    private final String TITLE = "Меню";
    @InjectPresenter
    CategoriesPresenter presenter;

    @ProvidePresenter
    CategoriesPresenter provideCategoriesPresenter() {
        return new CategoriesPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    private RecyclerView recView;
    private CategoryAdapter adapter;

    List<Category> categoryList;

    public static CategoriesFragment newInstance(Bundle args) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recView = view.findViewById(R.id.recViewCategories);
        recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recView.addItemDecoration(new GridItemDecorator(
                20));
/*        App.getDBRepository().addCategory(new Category("https://wallbox.ru/wallpapers/main/201241/eda-ed56f41d6f95.jpg", "Первое", "1"));
        App.getDBRepository().addCategory(new Category("http://puzzleit.ru/files/puzzles/110/110227/_original.jpg", "Второе", "2"));
        App.getDBRepository().addCategory(new Category("https://w-dog.ru/wallpapers/5/4/535619069087430.jpg", "Салаты", "3"));
        App.getDBRepository().addCategory(new Category("https://mota.ru/upload/wallpapers/source/2013/10/24/19/03/37906/9PSSW1BFnn.jpg", "Десерты", "4"));
        App.getDBRepository().addCategory(new Category("http://mastereat.ru/wp-content/uploads/2010/03/%D0%97%D0%B0%D0%BA%D1%83%D1%81%D0%BA%D0%B0-%D0%B8%D0%B7-%D0%B1%D0%B0%D0%BA%D0%BB%D0%B0%D0%B6%D0%B0%D0%BD%D0%BE%D0%B2..jpg", "Закуски", "5"));

        Eat eat = new Eat();
        eat.price = "122";
        eat.id = "1";
        eat.categoryId = "1";
        eat.description = "суп, главным ингредиентом которого является свежий, замороженный, консервированный или сушёный горох. Гороховый суп в различных вариациях является традиционным блюдом многих стран. Его цвет может быть бледно-зелёным либо жёлтым и зависит от сорта гороха.\n" +
                "\n" +
                "Гороховый суп известен человечеству с древности: так, упоминание о нём содержится в «Птицах» Аристофана. Греки и римляне выращивали горох ещё около 500—400 годов до н. э. В ту эпоху торговцы на улицах Афин продавали горячий гороховый суп.[1]";
        eat.title = "Суп гороховый";
        eat.imgUrl = "https://www.gastronom.ru/binfiles/images/20150327/b068e980.jpg";
        App.getDBRepository().addEat(eat);

        eat.price = "321";
        eat.id = "2";
        eat.description = " пюре из картофеля. Распространённый способ приготовления картофеля во всём мире.\n" +
                "\n" +
                "Просто измельчённый (размятый, протёртый) отваренный картофель некорректно называть картофельным пюре. Полноценное блюдо можно получить во-первых, вводя в картофель дополнительные ингредиенты — прежде всего, молоко, а также сливочное масло, иногда — яйца, а во-вторых, соблюдая технологию приготовления.\n" +
                "\n" +
                "Кроме того, в пюре могут добавляться ингредиенты, не составляющие однородную массу с пюре (такие, как бекон, обжаренный лук, кусочки паприки), однако стоит заметить, что это уже будут самостоятельные блюда, не являющиеся в чистом виде картофельным пюре.\n" +
                "\n" +
                "Картофельное пюре, как правило, употребляется в качестве гарнира, но может, в свою очередь, использоваться как ингредиент для приготовления других блюд.";
        eat.categoryId = "2";
        eat.title = "Пюрешка";
        eat.imgUrl = "http://kuking.net/resize/197/600/w/uploads/content/1403255083.jpg";
        App.getDBRepository().addEat(eat);

        Eat eat1 = new Eat();
        eat1.price = "70";
        eat.description = "длинные, похожие на волокна, стержни, изделия из высушенного теста (обычно из пшеничной муки с водой). Также выпускаемые промышленностью макаронные изделия могут иметь самую разнообразную форму — в виде рожков, чешуек, коротких цилиндров и других форм.\n" +
                "\n" +
                "Иногда при их изготовлении используется также мука из риса, гречихи, крахмала из бобов мунг и других зерновых культур.\n" +
                "\n" +
                "Обычно макаронные изделия выпускаются пищевой промышленностью и хранят в сухом виде. Их отваривают перед употреблением. Иногда в тесто при их изготовлении добавляются другие ингредиенты, например: красители (томат-паста, шпинат, свёкла, пигмент, выделяемый из каракатиц (чернила каракатиц) и другие), яйца, зелень.\n" +
                "\n" +
                "Часто термин «макаронные изделия» относится только к высушенным изделиям из теста, — полуфабрикатам. Однако некоторые изделия из теста, которые затем отваривают в кипящей воде, готовятся не только из сухого, но и из свежеприготовленного теста (например: лапша, ньокки, бешбармак). Точной, однозначной и общепринятой классификации макаронных изделий из теста не существует.";
        eat1.id = "3";
        eat1.categoryId = "2";
        eat1.title = "Макароны 'Al-dsa-ja-perdole, sipodal tapok sosichko'";
        eat1.imgUrl = "https://i.ytimg.com/vi/6IpR7_MKsoQ/hqdefault.jpg";
        App.getDBRepository().addEat(eat1);*/

        adapter = new CategoryAdapter(getActivity());
        adapter.setOnCategoryClickListener(this);
        recView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }


    @Override
    public void setCategories(List<Category> categoryList) {
        this.categoryList = categoryList;
        adapter.setCategoryList(categoryList);
    }

    @Override
    public void onCategoryClick(String id, String title) {
        presenter.navigateToEatFragment(id, title);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
