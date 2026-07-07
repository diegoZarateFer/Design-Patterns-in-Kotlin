package users

interface Observable<T> {
    val currentValue: T
    val observers: List<Observer<T>>

    fun registerObserver(observer: Observer<T>)
    fun notifyObservers() {
        for (observer in observers) {
            observer.onChanged(currentValue)
        }
    }

    fun unregisterObserver(observer: Observer<T>)
}