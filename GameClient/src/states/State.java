package states;

import structs.AnimalGame;

public interface State {

    void input();

    default void update() {
        update(1f / AnimalGame.TARGET_UPS);
    }

    void update(float delta);

    default void render() {
        render(1f);
    }

    void render(float alpha);

    void enter();

    void exit();
}
