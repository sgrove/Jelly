(ns jelly.graphics
  (:use net.philh.cloggle))

(defn new-window [text]
  (let [frame (new java.awt.Frame)
        canvas (new javax.media.opengl.GLCanvas)
        animator (new com.sun.opengl.util.Animator canvas)
        font (new java.awt.Font "SansSerif" 1 36)
        renderer (new com.sun.opengl.util.j2d.TextRenderer font)
        created (new java.util.Date)]
    (. canvas (addGLEventListener
                         (proxy [javax.media.opengl.GLEventListener] []
                           (init [x])
                           (reshape [#^javax.media.opengl.GLAutoDrawable drawable x y w h]
                                    (ogl/with-gl (.getGL drawable)
                                      (matrix-mode gl-projection)
                                      (load-identity)
                                      (ortho 0 1 0 1 -1 1)
                                      (matrix-mode gl-modelview)
                                      (load-identity)))
                           (display [#^javax.media.opengl.GLAutoDrawable drawable]
                                    (let [height (.getHeight drawable)
                                          width  (.getWidth  drawable)
                                          now (new java.util.Date)
                                          offset (int (/ (- (.getTime now) (.getTime created)) 100))]
                                      (ogl/with-gl (.getGL drawable)
                                        (clear gl-color-buffer-bit)
                                        (clear gl-depth-buffer-bit)
                                        (color 1 0.5 1)
                                        (with-primitive gl-triangles
                                          ;; test: vectors, doubles, ints, floats, ratios all work.
                                          (vertex 0 0.0)
                                          (vertex [1 0.5])
                                          (vertex (float 0.1) (/ 9 10))))
                                      (.beginRendering renderer width height)
                                      (.setColor renderer 1 1 1 0.8)
                                      (.draw renderer (str text) offset (/ height 2))
                                      (.endRendering renderer))))))
    (.setSize canvas 100 100)
    (.add frame canvas)
    (.addWindowListener frame
                        (proxy [java.awt.event.WindowAdapter] []
                          (windowClosing [event]
                                         (.dispose frame))))
    (. frame
       (addWindowListener
        (proxy [java.awt.event.WindowAdapter] []
          (windowClosing [event]
                         (. (new Thread
                                 (fn []
                                   (. animator stop)
                                   (. frame dispose))) start)))))
    (. animator start)
    (.pack frame)
    (.show frame)))
