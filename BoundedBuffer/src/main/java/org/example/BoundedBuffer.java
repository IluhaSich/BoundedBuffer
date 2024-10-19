package org.example;

class BoundedBuffer<T> {
    private final T[] buffer;
    private int count = 1000;
    private int in = 0;
    private int out = 0;
    private boolean ft = true;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int size) {
        buffer = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        if (buffer.length > in && ((in != out) || ft) && in + 1 != out)   {
            if (ft) {
                ft = false;
                System.out.println("Первое добавление");
            } // добавление первого эллемента не смотря на то что in = out
            buffer[in++] = item;
            count--;
        } else if (!(buffer.length > in) && out != 0) {
            in = 0;
            buffer[in] = item;
            count--;
        } else {
//            System.out.println("sleep");
            wait();
            put(item);
        }
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        if (buffer.length > out && out != in && out + 1 != in) {
            notifyAll();
            return buffer[out++];
        } else if (!(buffer.length > out) && in !=0) {
            out = 0;
            notifyAll();
            return buffer[out];
        } else {
//            System.out.println("take sleep l:" + buffer.length + " out:" + out + " in:" + in);
            wait();
        }

        return take();
    }

    public int getCount() {
        return count;
    }

    public T[] getBuffer() {
        return buffer;
    }
}
