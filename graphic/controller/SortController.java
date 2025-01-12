package org.example.graphic.controller;

import org.example.graphic.model.audio.Audio;

import java.util.ArrayList;
import java.util.Collections;

public class SortController
{
    public ArrayList<Audio> sort(ArrayList<Audio> sortedAudioFiles)
    {
        for(int i = 0;i<sortedAudioFiles.size();++i)
        {
            for(int j =0;j<sortedAudioFiles.size() ;++j) {
                if (sortedAudioFiles.get(i).compareTo(sortedAudioFiles.get(j)) > 0) {
                    Collections.swap(sortedAudioFiles, i, j);
                }
            }

        }
        return sortedAudioFiles;
    }
}
