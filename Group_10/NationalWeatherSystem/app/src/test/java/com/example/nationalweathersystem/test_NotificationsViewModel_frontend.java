package com.example.nationalweathersystem;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class test_NotificationsViewModel_frontend {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private NotificationsViewModel viewModel;
    private Observer<String> observer;

    @Before
    public void setUp() {
        viewModel = new NotificationsViewModel();
        observer = Mockito.mock(Observer.class);
        viewModel.getText().observeForever(observer);
    }

    @Test
    public void testInitialText() {
        assertEquals("This is notifications fragment", viewModel.getText().getValue());
    }

    @Test
    public void testSetText() {
        viewModel.getText().setValue("New Notification Text");
        assertEquals("New Notification Text", viewModel.getText().getValue());
        Mockito.verify(observer).onChanged("New Notification Text");
    }

    // Edge Test Cases

    // Set null value
    @Test
    public void testSetNullText() {
        viewModel.getText().setValue(null);
        assertNull(viewModel.getText().getValue());
        Mockito.verify(observer).onChanged(null);
    }

    // Set empty string
    @Test
    public void testSetEmptyText() {
        viewModel.getText().setValue("");
        assertEquals("", viewModel.getText().getValue());
        Mockito.verify(observer).onChanged("");
    }

    // Set very long string
    @Test
    public void testSetVeryLongText() {
        String longText = new String(new char[10000]).replace("\0", "a");
        viewModel.getText().setValue(longText);
        assertEquals(longText, viewModel.getText().getValue());
        Mockito.verify(observer).onChanged(longText);
    }
}
