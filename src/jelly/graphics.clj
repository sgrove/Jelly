(ns jelly.graphics
  (:use net.philh.cloggle))

(let [frame (new java.awt.Frame)
      canvas (new javax.media.opengl.GLCanvas)]
    (.addGLEventListener canvas
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
                  (ogl/with-gl (.getGL drawable)
                    (clear gl-color-buffer-bit)
                    (clear gl-depth-buffer-bit)
                    (color 1 0.5 1)
                    (with-primitive gl-triangles
		      ;; test: vectors, doubles, ints, floats, ratios all work.
                      (vertex 0 0.0)
                      (vertex [1 0.5])
                      (vertex (float 0.1) (/ 9 10)))))))
    (.setSize canvas 100 100)
    (.add frame canvas)
    (.addWindowListener frame
                        (proxy [java.awt.event.WindowAdapter] []
                          (windowClosing [event]
                                         (.dispose frame))))
    (.pack frame)
    (.show frame))
