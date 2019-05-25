package me.lonelee.droidlove.feature.recourse;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.lonelee.droidlove.feature.recourse.anime.AnimeFragment;
import me.lonelee.droidlove.feature.recourse.comic.ComicFragment;
import me.lonelee.droidlove.feature.recourse.game.GameFragment;

public class ResourcePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> pages = new ArrayList<>();
    private List<CharSequence> titles = new ArrayList<>();

    public ResourcePageAdapter(FragmentManager fm) {
        super(fm);
        pages.add(new AnimeFragment());
        pages.add(new ComicFragment());
        titles.add(AnimeFragment.TITLE);
        titles.add(ComicFragment.TITLE);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
