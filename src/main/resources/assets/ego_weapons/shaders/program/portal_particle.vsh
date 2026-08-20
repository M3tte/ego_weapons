#version 120

varying vec2 texCoord;
varying vec3 viewPos;
varying vec4 vertexColor;

void main() {

    texCoord = gl_MultiTexCoord0.xy;

    vertexColor = gl_Color;

    vec4 view =
    gl_ModelViewMatrix * gl_Vertex;

    viewPos = view.xyz;

    gl_Position =
    gl_ProjectionMatrix * view;
}